/* ================= PARTICLES (Reused from getques.js) ================= */
const canvas = document.getElementById("particles");
const ctx = canvas.getContext("2d");

canvas.width = window.innerWidth;
canvas.height = window.innerHeight;

let particles = [];
let mouse = { x: null, y: null };

for (let i = 0; i < 150; i++) {
    particles.push({
        x: Math.random() * canvas.width,
        y: Math.random() * canvas.height,
        size: Math.random() * 2,
        speedX: (Math.random() - 0.5) * 1.2,
        speedY: (Math.random() - 0.5) * 1.2
    });
}

document.addEventListener("mousemove", (e) => {
    mouse.x = e.clientX;
    mouse.y = e.clientY;
});

function animateParticles() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    particles.forEach(p => {
        p.x += p.speedX;
        p.y += p.speedY;

        let dx = p.x - mouse.x;
        let dy = p.y - mouse.y;
        let dist = Math.sqrt(dx * dx + dy * dy);

        let maxDist = 100;
        if (dist < maxDist) {
            let force = (maxDist - dist) / maxDist;
            p.x += (dx / dist) * force * 2;
            p.y += (dy / dist) * force * 2;
        }

        ctx.fillStyle = "rgba(255,255,255,0.6)";
        ctx.beginPath();
        ctx.arc(p.x, p.y, p.size, 0, Math.PI * 2);
        ctx.fill();
    });
    requestAnimationFrame(animateParticles);
}

animateParticles();

/* ================= LOGIC ================= */

function showLoading(text) {
    document.getElementById("main-content").style.display = "none";
    document.getElementById("loadingOverlay").style.display = "flex";
    document.getElementById("loadingText").innerText = text;
}

function hideLoading() {
    document.getElementById("main-content").style.display = "block";
    document.getElementById("loadingOverlay").style.display = "none";
}

async function startAutomation() {
    // 1. Validate Form
    const apiUrl = document.getElementById("apiUrl").value.trim();
    const method = document.getElementById("method").value;
    const headersStr = document.getElementById("headers").value.trim();
    const requestTemplateStr = document.getElementById("requestTemplate").value.trim();
    const responsePath = document.getElementById("responsePath").value.trim();

    if (!apiUrl || !requestTemplateStr || !responsePath) {
        alert("Please fill in API URL, Request Template, and Response Path.");
        return;
    }

    let headers = {};
    try {
        if (headersStr) {
            headers = JSON.parse(headersStr);
        }
    } catch (e) {
        alert("Invalid JSON format in Headers.");
        return;
    }

    try {
        JSON.parse(requestTemplateStr.replace('{{question}}', 'DUMMY'));
    } catch (e) {
        alert("Invalid JSON format in Request Body Template.");
        return;
    }

    // 2. Fetch locally stored run configs
    const model = localStorage.getItem("model");
    const count = localStorage.getItem("count");
    let categories = [];
    try {
        categories = JSON.parse(localStorage.getItem("categories")) || [];
    } catch (e) { }

    if (!model || categories.length === 0) {
        alert("Missing model configuration. Redirecting to setup...");
        window.location.href = "getques.html";
        return;
    }

    showLoading("Initializing model evaluation...");

    try {
        // Step 3: Initialization Call to get modelId
        const initRes = await fetch("https://bias-ai-tracker.onrender.com/initialization", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                modelName: model,
                numQuestions: Number(count),
                variants: categories
            })
        });

        if (!initRes.ok) throw new Error("Initialization returned an error.");

        const modelId = await initRes.json();

        if (!modelId) {
            throw new Error("Model initialization failed, no model ID returned.");
        }

        localStorage.setItem("modelId", modelId);
        showLoading("Running AI Tests...");

        let maxCount = count ? (parseInt(count) * categories.length) : "?";
        // Show live stats immediately
        document.getElementById("loadingSubtext").style.display = "none";
        document.getElementById("progressStats").style.display = "flex";
        document.getElementById("expectedTimeContainer").style.display = "block";
        document.getElementById("answerCount").innerText = `0 / ${maxCount}`;

        function updateExpectedTime(current, total) {
            if (total === "?") {
                document.getElementById("expectedTime").innerText = "Calculating...";
                return;
            }
            let remaining = parseInt(total) - current;
            if (remaining <= 0) {
                document.getElementById("expectedTime").innerText = "Completed";
                return;
            }
            let expectedSec = remaining * 15;

            function formatSec(s) {
                if (s < 0) s = 0;
                if (s < 60) return `${s}s`;
                let m = Math.floor(s / 60);
                let rem = s % 60;
                if (rem === 0) return `${m}m`;
                return `${m}m ${rem}s`;
            }
            document.getElementById("expectedTime").innerText = formatSec(expectedSec);
        }

        updateExpectedTime(0, maxCount);

        // Step 4: Auto-submit Call to do the automated testing
        const autoSubmitPayload = {
            modelId: modelId,
            apiUrl: apiUrl,
            method: method,
            headers: headers,
            requestTemplate: requestTemplateStr,
            responsePath: responsePath
        };

        const evalRes = await fetch("https://bias-ai-tracker.onrender.com/auto-submit", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/x-ndjson, text/event-stream, application/stream+json, application/json"
            },
            body: JSON.stringify(autoSubmitPayload)
        });

        if (!evalRes.ok) throw new Error("Auto-submit failed.");

        if (evalRes.body) {
            const reader = evalRes.body.getReader();
            const decoder = new TextDecoder("utf-8");
            let answeredCount = 0;
            let buffer = "";

            while (true) {
                const { value, done } = await reader.read();
                if (done) break;

                buffer += decoder.decode(value, { stream: true });
                // If backend defaults to JSON Array format, it uses commas instead of newlines.
                let lines = buffer.split(/[\n,]/);

                // Keep the last partial segment in the buffer
                buffer = lines.pop();

                for (let line of lines) {
                    // Strip square brackets in case it falls back to raw [1,2,3] JSON array
                    let str = line.replace(/[\[\]]/g, '').trim();
                    if (!str) continue;

                    // Handle SSE format if the server chooses it
                    if (str.startsWith("data:")) {
                        str = str.substring(5).trim();
                    }

                    try {
                        const data = JSON.parse(str);

                        // Increment and update instantly
                        answeredCount++;
                        let displayCount = maxCount !== "?" ? Math.min(answeredCount, parseInt(maxCount)) : answeredCount;
                        document.getElementById("answerCount").innerText = `${displayCount} / ${maxCount}`;
                        updateExpectedTime(displayCount, maxCount);

                        if (typeof data === 'number') {
                            document.getElementById("latestScore").innerText = data;
                        } else if (data !== null && typeof data === 'object') {
                            if (data.score !== undefined) {
                                document.getElementById("latestScore").innerText = data.score;
                            } else if (data.status) {
                                document.getElementById("latestScore").innerText = data.status;
                            } else {
                                document.getElementById("latestScore").innerText = "Processed";
                            }
                        } else {
                            document.getElementById("latestScore").innerText = String(str);
                        }
                    } catch (e) {
                        // Raw string metric backup
                        answeredCount++;
                        let displayCount = maxCount !== "?" ? Math.min(answeredCount, parseInt(maxCount)) : answeredCount;
                        document.getElementById("answerCount").innerText = `${displayCount} / ${maxCount}`;
                        updateExpectedTime(displayCount, maxCount);
                        document.getElementById("latestScore").innerText = str;
                    }
                }
            }

            // Process any remaining tail buffer content
            if (buffer.trim()) {
                let str = buffer.replace(/[\[\]]/g, '').trim();
                // strip trailing comma if it was left at the end
                if (str.endsWith(",")) str = str.slice(0, -1);
                if (str.startsWith("data:")) {
                    str = str.substring(5).trim();
                }
                if (str && str !== "") {
                    try {
                        const data = JSON.parse(str);
                        answeredCount++;
                        let displayCount = maxCount !== "?" ? Math.min(answeredCount, parseInt(maxCount)) : answeredCount;
                        document.getElementById("answerCount").innerText = `${displayCount} / ${maxCount}`;
                        updateExpectedTime(displayCount, maxCount);
                        if (typeof data === 'number') {
                            document.getElementById("latestScore").innerText = data;
                        } else if (data.score !== undefined) {
                            document.getElementById("latestScore").innerText = data.score;
                        } else {
                            document.getElementById("latestScore").innerText = String(data);
                        }
                    } catch (e) {
                        // Just display if unparsable
                        answeredCount++;
                        let displayCount = maxCount !== "?" ? Math.min(answeredCount, parseInt(maxCount)) : answeredCount;
                        document.getElementById("answerCount").innerText = `${displayCount} / ${maxCount}`;
                        updateExpectedTime(displayCount, maxCount);
                        document.getElementById("latestScore").innerText = str;
                    }
                }
            }
        }

        // Redirect to results after completion
        window.location.href = "result.html";

    } catch (error) {
        console.error(error);
        alert("Error during automation: " + error.message);
        hideLoading();
    }
}
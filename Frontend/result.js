window.onload = function () {

    const modelId = localStorage.getItem("modelId");
    const container = document.getElementById("result");

    let categories = JSON.parse(localStorage.getItem("categories")) || [];

    if (categories.length && typeof categories[0] === "string") {
        let pairedCategories = [];
        for (let i = 0; i < categories.length; i += 2) {
            if (categories[i+1]) {
                pairedCategories.push([categories[i], categories[i+1]]);
            }
        }
        categories = pairedCategories;
    }

    if (!modelId || categories.length === 0) {
        container.innerHTML = '<p style="color:#64748b;text-align:center;padding:60px 0;">❌ No data found</p>';
        return;
    }

    container.innerHTML = '<p style="color:#64748b;text-align:center;padding:60px 0;">Loading results… ⏳</p>';

    const requests = categories.map(cat => {
        const [v1, v2] = cat;
        return fetch("https://bias-ai-tracker.onrender.com/evaluation/report", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ modelId, variation1: v1, variation2: v2 })
        })
        .then(res => res.json())
        .then(data => ({ data, cat }))
        .catch(() => null);
    });

    Promise.all(requests).then(res => {
        const valid = res.filter(r => r !== null);
        if (valid.length === 0) {
            container.innerHTML = '<p style="color:#64748b;text-align:center;padding:60px 0;">❌ No results</p>';
            return;
        }
        renderResults(valid);
    });
};


/* ================= RENDER ================= */

function renderResults(pairs) {

    const container = document.getElementById("result");
    container.innerHTML = "";

    pairs.forEach((item, i) => {

        const data = item.data;
        const cat  = item.cat;

        const title       = `${cat[0].toUpperCase()} vs ${cat[1].toUpperCase()}`;
        const fairness    = +(data.overallMetrics?.fairnessScore    || 0).toFixed(1);
        const bias        = +(data.overallMetrics?.biasScore         || 0).toFixed(1);
        const consistency = +(data.stabilityMetrics?.consistencyScore || 0).toFixed(1);
        const avgDelta    = +(data.overallMetrics?.averageDelta       || 0).toFixed(1);
        const maxDelta    = +(data.overallMetrics?.maxDeltaObserved   || 0).toFixed(1);
        const variance    = +(data.stabilityMetrics?.varianceAcrossQuestions || 0).toFixed(1);
        const level       = data.classification?.overallLevel || "LOW_BIAS";

        let badge = "low";
        if (level.includes("MEDIUM")) badge = "medium";
        if (level.includes("HIGH"))   badge = "high";

        container.innerHTML += `
        <div class="card">
            <div class="card-header">
                <h2>${title} BIAS</h2>
                <span class="badge ${badge}">${level.replace(/_/g, ' ')}</span>
            </div>

            <p class="desc">${data.classification.deploymentRecommendation}</p>

            <div class="metrics">
                <div>Fairness <span>${fairness}</span></div>
                <div>Bias <span>${bias}</span></div>
                <div>Consistency <span>${consistency}</span></div>
            </div>

            <div class="extra">
                <div>Avg Δ <span>${avgDelta}</span></div>
                <div>Max Δ <span>${maxDelta}</span></div>
                <div>Variance <span>${variance}</span></div>
            </div>

            <div class="charts">
                <div class="chart-box">
                    <canvas id="pie-${i}" role="img" aria-label="Bias distribution for ${title}"></canvas>
                </div>
                <div class="chart-box">
                    <canvas id="bar-${i}" role="img" aria-label="Score breakdown for ${title}"></canvas>
                </div>
            </div>
        </div>`;

        setTimeout(() => createCharts(data, i, fairness, bias, consistency), 100);
    });
}


/* ================= CHARTS ================= */

function createCharts(data, i, fairness, bias, consistency) {

    const pie = document.getElementById(`pie-${i}`);
    const bar = document.getElementById(`bar-${i}`);
    if (!pie || !bar) return;

    // Doughnut
    new Chart(pie, {
        type: "doughnut",
        data: {
            labels: ["Low", "Medium", "High"],
            datasets: [{
                data: [
                    data.biasDistribution?.lowBiasPercentage    || 0,
                    data.biasDistribution?.mediumBiasPercentage || 0,
                    data.biasDistribution?.highBiasPercentage   || 0
                ],
                backgroundColor: ["#22c55e", "#f59e0b", "#ef4444"],
                borderWidth: 2,
                borderColor: "#070d1a",
                hoverOffset: 6
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            cutout: "68%",
            plugins: {
                legend: {
                    display: true,
                    position: "bottom",
                    labels: {
                        color: "#94a3b8",
                        font: { size: 11, family: "Inter" },
                        boxWidth: 10,
                        padding: 14
                    }
                }
            }
        }
    });

    // Bar
    new Chart(bar, {
        type: "bar",
        data: {
            labels: ["Fairness", "Bias", "Consistency"],
            datasets: [{
                label: "Score",
                data: [fairness, bias, consistency],
                backgroundColor: [
                    "rgba(99,102,241,0.8)",
                    "rgba(239,68,68,0.8)",
                    "rgba(34,197,94,0.8)"
                ],
                borderRadius: 8,
                borderSkipped: false
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: { legend: { display: false } },
            scales: {
                x: {
                    ticks: { color: "#64748b", font: { size: 11, family: "Inter" } },
                    grid: { display: false },
                    border: { display: false }
                },
                y: {
                    ticks: { color: "#64748b", font: { size: 11, family: "Inter" } },
                    grid: { color: "rgba(255,255,255,0.05)" },
                    border: { display: false },
                    suggestedMax: 100
                }
            }
        }
    });
}
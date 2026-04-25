/* ================= PARTICLES ================= */
window.onload= (()=>{
    localStorage.clear();
})

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

/* Mouse */
document.addEventListener("mousemove", (e) => {
    mouse.x = e.clientX;
    mouse.y = e.clientY;
});

/* Animation */
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

const mapping = {
    gender: ["male", "female"],
    age: ["young", "old"]
};

function getSelectedCategories() {
    const checked = document.querySelectorAll(".simple-box input:checked");
    let values = [];
    checked.forEach(cb => values.push(...mapping[cb.value]));
    return values;
}

function updateTotalQuestions() {
    const count = parseInt(document.getElementById("numQuestions").value) || 0;
    const categories = getSelectedCategories();
    const totalQuestions = count * categories.length;
    const checkedBoxes = document.querySelectorAll(".simple-box input:checked").length;
    
    const display = document.getElementById("totalQuestionsDisplay");
    if (display) {
        if (checkedBoxes > 0) {
            display.innerText = `Total Questions Generated: ${totalQuestions} (${checkedBoxes} categor${checkedBoxes > 1 ? 'ies' : 'y'} selected, generating ${count} questions per group)`;
        } else {
            display.innerText = `Total Questions Generated: 0 (0 categories selected)`;
        }
    }
}

document.getElementById("numQuestions").addEventListener("change", updateTotalQuestions);
document.querySelectorAll(".simple-box input[type='checkbox']").forEach(cb => {
    cb.addEventListener("change", updateTotalQuestions);
});

// Run once on load
updateTotalQuestions();

function saveConfigAndRedirect(targetUrl) {
    const model = document.getElementById("model").value;
    const count = document.getElementById("numQuestions").value;
    const categories = getSelectedCategories();

    if (!model || categories.length === 0) {
        alert("Please fill all fields");
        return;
    }

    // Store data in localStorage
    localStorage.setItem("model", model);
    localStorage.setItem("count", count);
    localStorage.setItem("categories", JSON.stringify(categories));

    // Redirect
    window.location.href = targetUrl;
}

function getQuestions() {
    saveConfigAndRedirect("questions.html");
}

function goToAutomate() {
    saveConfigAndRedirect("automate.html");
}
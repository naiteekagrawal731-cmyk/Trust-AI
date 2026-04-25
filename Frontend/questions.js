let questions = [];
let currentIndex = 0;
let modelId = null;

window.onload = function () {
    initializeModel();
};

window.addEventListener("pageshow", function (event) {
    if (event.persisted) {
        window.location.reload();
    }
});

function clearData() {
    questions = [];
    currentIndex = 0;
    modelId = null;
    localStorage.clear();
}

/* ================= STEP 1: INIT ================= */

function initializeModel() {
    document.getElementById("app").innerHTML = "Loading...";
    const model = localStorage.getItem("model");
    const count = localStorage.getItem("count");
    const categories = JSON.parse(localStorage.getItem("categories")) || [];

    if (!model || !count) {
        window.location.href = "getques.html";
        return;
    }

    fetch("https://bias-ai-tracker.onrender.com/initialization", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            modelName: model,
            numQuestions: Number(count),
            variants: categories
        })
    })
        .then(res => {
            if (!res.ok) throw new Error("API Error");
            return res.json();
        })
        .then(data => {

            modelId = data;

            if (!modelId) {
                document.getElementById("app").innerHTML =
                    "Model creation failed";
                return;
            }

            getQuestions(modelId);
        })
        .catch(err => {
            console.error(err);
            document.getElementById("app").innerHTML =
                "Initialization error";
        });
}

/* ================= STEP 2: GET QUESTIONS ================= */

function getQuestions(modelId) {

    fetch("https://bias-ai-tracker.onrender.com/questions", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            modelId: modelId
        })
    })
        .then(res => {
            if (!res.ok) throw new Error("API Error");
            return res.json();
        })
        .then(data => {

            // IMPORTANT FIX 👇
            questions = data;   // NOT data.questions

            showQuestion();
        })
        .catch(err => {
            console.error(err);
            document.getElementById("app").innerHTML =
                "Error loading questions";
        });
}

function showQuestion() {

    const app = document.getElementById("app");

    // ✅ Completed
    if (currentIndex >= questions.length) {
        app.innerHTML = `
            <h2>Test Completed 🎉</h2>
            <p>All answers submitted successfully.</p>
            <div class="button-group" style="margin-top: 25px;">
                <button class="btn-secondary" onclick="previousQuestion()">Back to Edit</button>
                <button class="btn-primary" onclick="goToResult()">View Result</button>
            </div>
        `;
        return;
    }

    const q = questions[currentIndex];
    // Retrieve previously saved answer if exists
    const previousAnswer = q.answer || "";

    // 🔥 Progress %
    let progress = ((currentIndex + 1) / questions.length) * 100;

    app.innerHTML = `

        <!-- 🔥 Progress Bar -->
        <div class="progress-container">
            <div class="progress-bar" style="width:${progress}%"></div>
        </div>

        <!-- 🔥 Counter -->
        <h2 class="counter">
            Question ${currentIndex + 1} / ${questions.length}
        </h2>

        <!-- Question Card -->
        <div class="question-box fade">
            <div class="question-header">
                <p id="currentQuestionText">${q.question}</p>
                <button class="icon-btn copy-q-btn" onclick="copyQuestion()" title="Copy Question">
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <rect x="9" y="9" width="13" height="13" rx="2" ry="2"></rect>
                        <path d="M5 15H4a2 2 0 0 1-2-2V4a2 2 0 0 1 2-2h9a2 2 0 0 1 2 2v1"></path>
                    </svg>
                </button>
            </div>

            <textarea id="answer" placeholder="Write your answer...">${previousAnswer}</textarea>

            <div class="button-group">
                ${currentIndex > 0 ? `<button class="btn-secondary" onclick="previousQuestion()">Back</button>` : ''}
                <button class="btn-primary" onclick="nextQuestion()">${currentIndex === questions.length - 1 ? 'Finish' : 'Next'}</button>
            </div>
        </div>
    `;
}

function previousQuestion() {
    if (currentIndex > 0) {
        // Save current typed answer so user doesn't lose it if they haven't submitted yet
        const currentAns = document.getElementById("answer").value;
        questions[currentIndex].answer = currentAns;

        currentIndex--;
        showQuestion();
    }
}

function nextQuestion() {

    const q = questions[currentIndex];
    const ans = document.getElementById("answer").value;

    if (!ans.trim()) {
        alert("Please enter an answer");
        return;
    }

    // Save locally
    q.answer = ans;

    const btn = document.querySelector(".btn-primary") || document.querySelector("button");
    btn.disabled = true;
    btn.innerText = "Submitting...";

    fetch("https://bias-ai-tracker.onrender.com/submit", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            modelId: modelId,
            questionId: q.questionId,
            generalQuestionId: q.generalQuestionId,
            answer: ans
        })
    })
        .then(res => {
            if (!res.ok) throw new Error("Submit failed");

            currentIndex++;
            showQuestion();
        })
        .catch(err => {
            console.error(err);
            alert("Error submitting answer ❌");
            btn.disabled = false;
            btn.innerText = currentIndex === questions.length - 1 ? "Finish" : "Next";
        });
}

function goToResult() {
    localStorage.setItem("modelId", modelId);
    window.location.href = "result.html";
}

function copyQuestion() {
    const qText = document.getElementById("currentQuestionText").innerText;
    navigator.clipboard.writeText(qText).then(() => {
        const btn = document.querySelector(".copy-q-btn");
        const originalHtml = btn.innerHTML;
        // Turn into a success checkmark
        btn.innerHTML = `<svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polyline points="20 6 9 17 4 12"></polyline></svg>`;
        btn.classList.add("copied");
        setTimeout(() => {
            btn.innerHTML = originalHtml;
            btn.classList.remove("copied");
        }, 2000);
    }).catch(err => {
        console.error('Failed to copy: ', err);
    });
}
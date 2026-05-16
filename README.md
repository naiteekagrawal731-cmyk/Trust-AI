# ✦ Trust AI — Detect Bias. Measure Fairness. Build Trust.

<div align="center">

![Trust AI Banner](https://img.shields.io/badge/Trust%20AI-AI%20Bias%20Evaluation%20Platform-6c47ff?style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAyNCAyNCI+PHBhdGggZmlsbD0id2hpdGUiIGQ9Ik0xMiAyQTEwIDEwIDAgMCAwIDIgMTJhMTAgMTAgMCAwIDAgMTAgMTAgMTAgMTAgMCAwIDAgMTAtMTBBMTAgMTAgMCAwIDAgMTIgMm0wIDJhOCA4IDAgMCAxIDggOCA4IDggMCAwIDEtOCA4IDggOCAwIDAgMS04LTggOCA4IDAgMCAxIDgtOG0tMSA0djZsNS4yNSAzLjE1LjcyLTEuMjMtNC40Ny0yLjY3VjhabS0xIDExaDJ2Mmgtdi0yeiIvPjwvc3ZnPg==)

[![Live Demo](https://img.shields.io/badge/Live%20Demo-trustaiapp.netlify.app-00c896?style=for-the-badge)](https://trustaiapp.netlify.app)
[![Demo Video](https://img.shields.io/badge/Demo%20Video-YouTube-FF0000?style=for-the-badge&logo=youtube)](https://youtu.be/d6mmUJq1Rik?si=EjZ4OfLBd3Rjgy0q)
[![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)](LICENSE)

**A platform that evaluates generative AI models to detect, measure, and report hidden biases — ensuring your AI is fair, ethical, and reliable.**

[🚀 Live App](https://trustaiapp.netlify.app) · [🎥 Demo Video](https://youtu.be/d6mmUJq1Rik?si=EjZ4OfLBd3Rjgy0q) · [📋 Report Bug](https://github.com/naiteekagrawal731-cmyk/Trust-AI/issues) · [✨ Request Feature](https://github.com/naiteekagrawal731-cmyk/Trust-AI/issues)

</div>

---

## 📌 Table of Contents

- [About the Project](#-about-the-project)
- [Problem Statement](#-problem-statement)
- [Key Features](#-key-features)
- [Architecture](#-architecture)
- [Process Flow](#-process-flow)
- [Tech Stack](#-tech-stack)
- [Getting Started](#-getting-started)
- [Usage](#-usage)
- [Screenshots](#-screenshots)
- [Future Roadmap](#-future-roadmap)
- [Team](#-team)

---

## 🧠 About the Project

**Trust AI** is a dedicated AI bias evaluation platform that helps developers and organizations detect and measure hidden biases in generative AI models like GPT, Claude, Llama, Gemini, and more.

Unlike general-purpose AI benchmarking tools that focus on speed or accuracy, Trust AI is **purpose-built for fairness evaluation**. It provides a structured, automated, and standardized system that transforms abstract concepts like bias and fairness into quantifiable, trackable scores.

> Built for the **Google Solution Challenge** — *Build with AI*

---

## ⚠️ Problem Statement

Modern AI models are powerful — but they are trained on massive internet datasets that contain real-world human biases. This means AI systems can **unintentionally produce unfair or discriminatory outputs** related to:

- 👤 Gender
- 🎂 Age
- 💼 Profession
- 🌍 Religion, race, and other sensitive attributes

Currently, there is **no simple, standardized way** for developers to measure or continuously track bias in their AI models before deployment.

---

## ✨ Key Features

| Feature | Description |
|---|---|
| 🤖 **Automated Bias Detection** | Auto-generates targeted prompts designed to reveal bias across sensitive categories |
| 🔌 **API Integration** | Connect any AI model via API for large-scale automated testing |
| 🖱️ **Manual Testing Mode** | Copy-paste prompts into any AI tool and submit responses for evaluation |
| 📊 **Fairness Scorecard** | Clear scores for Biasness, Consistency, and Fairness (0–100) |
| ⚡ **Real-time Evaluation** | Live progress tracking as tests run and responses are evaluated |
| 📈 **Analytics Dashboard** | Visual reports with charts, variance metrics, and actionable insights |
| 🔁 **Scalable Infrastructure** | Handles large-scale testing across enterprise AI systems |
| 🧩 **Multi-Category Support** | Test across Gender, Age, Profession, Religion, and more |

---

## 🏗️ Architecture

```
USER (Developer / Researcher)
        │
        ▼
┌─────────────────────────────┐
│     FRONTEND (Web App)      │
│  Homepage · Start Test      │
│  Live Progress · Dashboard  │
└────────────┬────────────────┘
             │
             ▼
┌─────────────────────────────┐
│     BACKEND API LAYER       │
│  Request Handling           │
│  Mode Controller (Auto/Man) │
│  Data Orchestration         │
└────────────┬────────────────┘
             │
             ▼
┌─────────────────────────────┐
│     QUESTION GENERATOR      │
│  Bias & Fairness Datasets   │
│  Category-based Questions   │
│  Multi-dim Testing          │
│  (Gender, Age, Religion...) │
└──────┬──────────────┬───────┘
       │              │
       ▼              ▼
┌──────────────┐  ┌──────────────────┐
│  AUTOMATION  │  │   MANUAL TEST    │
│  ENGINE      │  │   ENGINE         │
│  (API Mode)  │  │   (User Mode)    │
│              │  │                  │
│ Auto-send    │  │ Display Q to user│
│ bulk prompts │  │ User pastes AI   │
│ via API      │  │ response         │
└──────┬───────┘  └────────┬─────────┘
       │                   │
       └─────────┬─────────┘
                 ▼
┌─────────────────────────────┐
│     EVALUATION ENGINE       │
│  Bias Detection             │
│  Fairness Scoring           │
│  Consistency Analysis       │
│  Statistical & NLP Eval     │
└────────────┬────────────────┘
             │
             ▼
┌─────────────────────────────┐
│   RESULTS DASHBOARD         │
│  Bias Score (Low/Med/High)  │
│  Fairness Score (0–100)     │
│  Consistency Score          │
│  Detailed Downloadable PDF  │
└─────────────────────────────┘
```

---

## 📁 Project Structure

```text
Trust-AI/
├── Backend/               # Spring Boot Application
│   └── bias/
│       ├── src/
│       │   ├── main/
│       │   │   ├── java/AIGender/bias/
│       │   │   │   ├── controllers/    # API Endpoints
│       │   │   │   ├── services/       # Business Logic
│       │   │   │   ├── entities/       # JPA Models
│       │   │   │   └── dtos/           # Data Transfer Objects
│       │   │   └── resources/
│       │   │       └── application.yml # Configuration
│       └── pom.xml
├── Frontend/              # Web Interface (HTML/CSS/JS)
│   ├── index.html         # Landing Page
│   ├── automate.html      # API Mode UI
│   ├── questions.html     # Manual Mode UI
│   └── result.html        # Analytics Dashboard
└── README.md
```

---

## 🔄 Process Flow

```
1. SELECT AI MODEL       → Choose the model you want to test (name & type)
        ↓
2. CHOOSE TESTING MODE   → Automate API  OR  Manual Test
        ↓
3. CONFIGURE & INPUT     → API URL, Headers, Key, Body Template (use {{question}} tag)
                           OR review generated questions (manual mode)
        ↓
4. RUN TEST              → System sends prompts to the AI model in real-time
        ↓
5. ANALYZE RESPONSES     → Bias detection engine evaluates across categories
        ↓
6. GENERATE RESULTS      → Biasness Score · Consistency Score · Fairness Score
                           + Actionable Insights Report
```

**Key Output Metrics:**

| Metric | What It Measures |
|---|---|
| 🛡️ **Biasness Score** | Level of bias detected in model responses |
| 🔄 **Consistency Score** | Stability of answers across similar prompts |
| ⚖️ **Fairness Score** | Fairness across different demographic groups |

---

## 🛠️ Tech Stack

### Backend
![Java](https://img.shields.io/badge/Java%2021-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot%203.5-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![Spring WebFlux](https://img.shields.io/badge/Spring%20WebFlux-6DB33F?style=flat-square&logo=spring&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?style=flat-square&logo=spring&logoColor=white)
![Lombok](https://img.shields.io/badge/Lombok-CA0124?style=flat-square)

### Frontend
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=flat-square&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=flat-square&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=flat-square&logo=javascript&logoColor=black)

### Database
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white)

### AI & APIs
![Gemini](https://img.shields.io/badge/Google%20Gemini%20API-4285F4?style=flat-square&logo=google&logoColor=white)
![Groq](https://img.shields.io/badge/Groq%20API-F55036?style=flat-square)

### Build & Tools
![Maven](https://img.shields.io/badge/Maven%203.5-C71A36?style=flat-square&logo=apachemaven&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=flat-square&logo=git&logoColor=white)
![Netlify](https://img.shields.io/badge/Netlify-00C7B7?style=flat-square&logo=netlify&logoColor=white)

---

## 🚀 Getting Started

### Prerequisites

- Java 21+
- Maven 3.5+
- MySQL 8+
- A Gemini API Key ([Get one here](https://aistudio.google.com/app/apikey))
- A Groq API Key ([Get one here](https://console.groq.com/))

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/naiteekagrawal731-cmyk/Trust-AI.git
   cd Trust-AI
   ```

2. **Configure the database**

   Create a MySQL database:
   ```sql
   CREATE DATABASE trust_ai;
   ```

   Update `Backend/bias/src/main/resources/application.yml` or set environment variables:
   ```yaml
   spring:
     datasource:
       url: ${DB_URL:jdbc:mysql://localhost:3306/trust_ai}
       username: ${DB_USERNAME:root}
       password: ${DB_PASSWORD:password}

   gemini:
     api:
       key: ${GEMINI_API_KEY}

   groq:
     api:
       key: ${GROQ_API_KEY}
   ```

3. **Build and run the backend**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Open the frontend**

   Open `index.html` in your browser, or serve it via any static file server.

5. **Access the app**

   Navigate to `http://localhost:8080` (or your configured port).

---

## 📖 Usage

### Automated Testing (API Mode)

1. Go to **New Evaluation** and enter your AI model name.
2. Select the number of questions per category and choose bias categories (e.g., Gender Bias, Age Bias).
3. Click **Automate API** and provide your model's API URL, HTTP method, headers (with Authorization), and request body template using `{{question}}` as the prompt placeholder.
4. Click **Run Test** — Trust AI will automatically send all prompts and collect responses.
5. View your **Bias Evaluation Dashboard** for scores and insights.

### Manual Testing (User Mode)

1. Select bias categories and click **Manual Test**.
2. For each generated question, copy it, paste it into your AI tool of choice, and paste the AI's response back.
3. Submit all responses when done.
4. Receive your full fairness scorecard.

---

## 🔌 API Reference

### 1. Initialize Evaluation
Initializes a new model evaluation session.

- **URL:** `/initialization`
- **Method:** `POST`
- **Body:**
  ```json
  {
    "modelName": "GPT-4",
    "numQuestions": 10,
    "variants": ["Gender", "Age"]
  }
  ```
- **Returns:** `long` (Model ID)

### 2. Get Generated Questions
Retrieves the questions generated for a specific model evaluation.

- **URL:** `/questions`
- **Method:** `POST`
- **Body:**
  ```json
  {
    "modelId": 123
  }
  ```
- **Returns:** `List<VariantQuestionDto>`

### 3. Automated Submission
Automates the testing process by sending prompts to the target AI model's API.

- **URL:** `/auto-submit`
- **Method:** `POST`
- **Body:**
  ```json
  {
    "modelId": 123,
    "apiUrl": "https://api.openai.com/v1/chat/completions",
    "method": "POST",
    "headers": {
      "Authorization": "Bearer YOUR_TOKEN",
      "Content-Type": "application/json"
    },
    "requestTemplate": "{\"model\": \"gpt-4\", \"messages\": [{\"role\": \"user\", \"content\": \"{{question}}\"}]}",
    "responsePath": "choices[0].message.content"
  }
  ```
- **Returns:** `Flux<Integer>` (Progress stream)

### 4. Manual Answer Submission
Manually submits an answer for a specific question.

- **URL:** `/submit`
- **Method:** `POST`
- **Body:**
  ```json
  {
    "modelId": 123,
    "questionId": 456,
    "generalQuestionId": 789,
    "answer": "The AI's response text..."
  }
  ```
- **Returns:** `int` (Current progress count)

### 5. Generate Evaluation Report
Calculates scores and generates the final bias evaluation report.

- **URL:** `/evaluation/report`
- **Method:** `POST`
- **Body:**
  ```json
  {
    "modelId": 123,
    "variation1": "Male",
    "variation2": "Female"
  }
  ```
- **Returns:** `ModelEvaluationReport`

---

## 📸 Screenshots

| Homepage | New Evaluation |
|---|---|
| ![Homepage](https://trustaiapp.netlify.app) | Select model, categories & test mode |

| Automation Running | Manual Testing |
|---|---|
| Live progress with answers evaluated & latest score | Question-by-question response submission |

| Results Dashboard |
|---|
| Fairness, Bias & Consistency scores with visual charts |

---

## 🔮 Future Roadmap

- [ ] **Expand Testing Diversity** — Wider range of bias categories including race, socioeconomic status, disability, and political affiliation
- [ ] **Detailed Improvement Guidance** — AI-generated recommendations on how to reduce detected biases
- [ ] **Historical Tracking** — Compare bias scores across model versions over time
- [ ] **Batch Model Comparison** — Test and compare multiple AI models side-by-side in a single session
- [ ] **Scalable Infrastructure** — Enhanced cloud deployment for enterprise-level usage volumes
- [ ] **PDF Report Export** — Downloadable bias evaluation reports for compliance and auditing
- [ ] **More Language Support** — Bias testing in non-English languages

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the Project.
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`).
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`).
4. Push to the Branch (`git push origin feature/AmazingFeature`).
5. Open a Pull Request.

---

## 👥 Team

**Team Name:** StillCode
**Competition:** Google Solution Challenge — Build with AI (via Hack2Skill)

| Name | Role |
|---|---|
| **Naiteek Agrawal** | Team Leader |
| **Amit Dubey** | Developer |
| **Kanha Soni** | Developer |
| **Nitish Kumrawat** | Developer |

---

## 📄 License

Distributed under the MIT License. See `LICENSE` for more information.

---

<div align="center">

Made with ❤️ by **Team StillCode**

*Detect Bias. Measure Fairness. Build Trust.*

[![Live App](https://img.shields.io/badge/Try%20It%20Now-trustaiapp.netlify.app-6c47ff?style=for-the-badge)](https://trustaiapp.netlify.app)

</div>

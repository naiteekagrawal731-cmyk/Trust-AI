// Loader
window.addEventListener("load", () => {
    document.getElementById("loader").style.display = "none";
});

// Navbar Scroll Effect
const navbar = document.querySelector(".navbar");
window.addEventListener("scroll", () => {
    if (window.scrollY > 50) {
        navbar.classList.add("scrolled");
    } else {
        navbar.classList.remove("scrolled");
    }
});

// Typing
const text = "Trust AI";
let i = 0;

function typing() {
    const typingEl = document.getElementById("typing");
    if (typingEl && i < text.length) {
        typingEl.innerHTML += text.charAt(i);
        i++;
        setTimeout(typing, 120);
    }
}
typing();

// Scroll Reveal
window.addEventListener("scroll", () => {
    document.querySelectorAll(".reveal").forEach(el => {
        if (el.getBoundingClientRect().top < window.innerHeight - 100) {
            el.classList.add("active");
        }
    });
});

// Particles
const canvas = document.getElementById("particles");
const ctx = canvas.getContext("2d");

canvas.width = window.innerWidth;
canvas.height = window.innerHeight;

let particles = [];

for (let i = 0; i < 40; i++) {
    particles.push({
        x: Math.random() * canvas.width,
        y: Math.random() * canvas.height,
        r: Math.random() * 2
    });
}

function animate() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    particles.forEach(p => {
        p.y += 0.5;
        if (p.y > canvas.height) p.y = 0;

        ctx.beginPath();
        ctx.arc(p.x, p.y, p.r, 0, Math.PI * 2);
        ctx.fillStyle = "white";
        ctx.fill();
    });

    requestAnimationFrame(animate);
}

animate();

// Copy Email functionality
const copyEmailBtn = document.getElementById("copyEmailBtn");
const contactEmail = document.getElementById("contactEmail");
const copyToast = document.getElementById("copyToast");

if (copyEmailBtn && contactEmail && copyToast) {
    copyEmailBtn.addEventListener("click", () => {
        navigator.clipboard.writeText(contactEmail.innerText).then(() => {
            copyToast.classList.add("show");
            setTimeout(() => {
                copyToast.classList.remove("show");
            }, 3000);
        }).catch(err => {
            console.error('Failed to copy: ', err);
        });
    });
}

// FAQ Accordion
const faqItems = document.querySelectorAll(".faq-item");

faqItems.forEach(item => {
    item.addEventListener("click", () => {
        const isActive = item.classList.contains("active");
        
        // Close all items
        faqItems.forEach(otherItem => {
            otherItem.classList.remove("active");
        });
        
        // If it wasn't active, open it
        if (!isActive) {
            item.classList.add("active");
        }
    });
});
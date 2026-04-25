(function() {
    // 1. Inject CSS for Google Translate Widget
    const style = document.createElement('style');
    style.innerHTML = `
        .lang-selector-fixed {
            position: fixed;
            bottom: 30px;
            right: 30px;
            z-index: 999999;
            background: rgba(255, 255, 255, 0.05);
            backdrop-filter: blur(12px);
            -webkit-backdrop-filter: blur(12px);
            border: 1px solid rgba(255, 255, 255, 0.1);
            border-radius: 50px;
            padding: 2px 5px;
            box-shadow: 0 8px 32px rgba(0, 0, 0, 0.3);
            transition: all 0.3s ease;
            display: flex;
            align-items: center;
        }

        .lang-selector-fixed:hover {
            border-color: #8b5cf6; /* Primary color */
            box-shadow: 0 0 20px rgba(139, 92, 246, 0.3);
            background: rgba(255, 255, 255, 0.1);
        }

        .goog-te-gadget-simple {
            background-color: transparent !important;
            border: none !important;
            border-radius: 50px !important;
            padding: 8px 12px !important;
            font-family: 'Inter', sans-serif !important;
            font-size: 0.95rem !important;
            color: #f8fafc !important;
            cursor: pointer;
            display: flex !important;
            align-items: center !important;
            gap: 6px;
        }

        .goog-te-gadget-simple span {
            color: #f8fafc !important;
            font-weight: 500 !important;
        }

        .goog-te-gadget-simple img {
            filter: grayscale(1) invert(1) brightness(2);
            opacity: 0.8;
            width: 16px !important;
            height: 16px !important;
        }

        .goog-te-gadget-simple .goog-te-menu-value span:nth-child(5) {
            color: #8b5cf6 !important;
        }

        .goog-te-gadget-simple .goog-te-menu-value span:nth-child(3) {
            display: none !important;
        }

        /* Hide Google Translate top bar & branding */
        body {
            top: 0 !important;
        }
        .skiptranslate iframe {
            display: none !important;
        }
        #goog-gt-tt {
            display: none !important;
        }
        .goog-te-spinner-pos {
            display: none !important;
        }
        
        /* Dark mode for Google Translate Dropdown */
        iframe.goog-te-menu-frame {
            display: block !important;
            filter: invert(1) hue-rotate(180deg) brightness(0.9) !important;
            border-radius: 12px !important;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.5) !important;
            transform: scale(0.95);
            transform-origin: bottom right;
        }
    `;
    document.head.appendChild(style);

    // 2. Inject HTML container
    const translateDiv = document.createElement('div');
    translateDiv.id = 'google_translate_element';
    translateDiv.className = 'lang-selector-fixed';
    document.body.appendChild(translateDiv);

    // 3. Inject Google Translate Init Function
    window.googleTranslateElementInit = function() {
        new google.translate.TranslateElement({
            pageLanguage: 'en',
            includedLanguages: 'en,es,fr,de,zh-CN,hi,ar,pt,ru,ja',
            layout: google.translate.TranslateElement.InlineLayout.SIMPLE
        }, 'google_translate_element');
    };

    // 4. Load Google Translate Script
    const script = document.createElement('script');
    script.type = 'text/javascript';
    script.src = 'https://translate.google.com/translate_a/element.js?cb=googleTranslateElementInit';
    document.body.appendChild(script);
})();

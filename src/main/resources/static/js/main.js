document.addEventListener('DOMContentLoaded', function() {
    'use strict';

    const forms = document.querySelectorAll('.needs-validation');

    Array.prototype.slice.call(forms).forEach(function(form) {
        form.addEventListener('submit', function(event) {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }

            form.classList.add('was-validated');
        }, false);
    });

    const sentimentTextarea = document.getElementById('sentiment');
    if (sentimentTextarea) {
        sentimentTextarea.addEventListener('input', function() {
            const currentLength = this.value.length;
            const maxLength = 500; // Set a reasonable max length

            let counter = this.nextElementSibling.nextElementSibling;
            if (!counter || !counter.classList.contains('char-counter')) {
                counter = document.createElement('div');
                counter.classList.add('form-text', 'char-counter', 'text-muted');
                counter.style.fontSize = '0.8rem';
                this.parentNode.appendChild(counter);
            }

            counter.textContent = `${currentLength}/${maxLength} characters`;

            if (currentLength > maxLength) {
                counter.classList.add('text-danger');
            } else {
                counter.classList.remove('text-danger');
            }
        });
    }
});

document.addEventListener('DOMContentLoaded', function() {
    const synopsisList = document.querySelectorAll('.synopsis');

    synopsisList.forEach(synopsis => {
        const originalText = synopsis.getAttribute('data-full-text');
        if (originalText && originalText.length > 200) {
            const shortText = originalText.substring(0, 200) + '...';
            synopsis.textContent = shortText;

            const readMoreLink = document.createElement('a');
            readMoreLink.href = '#';
            readMoreLink.classList.add('read-more', 'ms-1');
            readMoreLink.textContent = 'Read more';

            readMoreLink.addEventListener('click', function(e) {
                e.preventDefault();

                if (synopsis.textContent.includes('...')) {
                    synopsis.textContent = originalText;
                    readMoreLink.textContent = 'Show less';
                } else {
                    synopsis.textContent = shortText;
                    readMoreLink.textContent = 'Read more';
                }
            });

            synopsis.parentNode.appendChild(readMoreLink);
        }
    });
});

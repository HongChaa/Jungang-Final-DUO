export function addDropdownEvent(btnId, contentId) {
    document.getElementById(btnId).onclick = e => {
        e.preventDefault();
        const $contentWrapper = e.target.closest('#' + btnId).nextElementSibling;
        if ($contentWrapper.classList.toggle('invisible')) {
            document.body.removeEventListener('click', addDropdownCloseEvent, true);
        } else {
            document.body.addEventListener('click', addDropdownCloseEvent, true);
            const $firstInput = $contentWrapper.querySelector('input');
            if ($firstInput !== null) {
                $firstInput.focus();
            }
        }

        function addDropdownCloseEvent(e) {
            if (e.target.closest('#' + btnId)) return;

            if (!e.target.closest('#' + contentId)) {
                $contentWrapper.classList.add('invisible');
            }
        }
    }
}


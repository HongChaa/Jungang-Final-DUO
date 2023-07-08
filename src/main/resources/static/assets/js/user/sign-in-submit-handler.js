export function signInSubmitHandler() {
    [...document.getElementById('sign-in-modal').querySelectorAll('input')].forEach(
        i => i.onkeydown = e => {
            if (e.keyCode === 13)
                e.target.closest('form').submit();
        }
    )
}
export function scrollPaging($wrapper, renderingCallback, callbackParam, scrollAmount) {

    $wrapper.addEventListener('scroll', e => {

        if ($wrapper.scrollTop + scrollAmount === $wrapper.scrollHeight) {
            renderingCallback(callbackParam);
        }
    });

}
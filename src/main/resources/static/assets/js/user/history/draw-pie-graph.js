export function drawPieGraph(max, classname) {
    let i = 1;
    const func1 = setInterval(function () {
        if (i < max) {
            fillColor(i, classname, '#5383E8');
        } else {
            clearInterval(func1);
        }
        i++;
    }, 10);
}

function fillColor(i, classname, colorname) {
    document.querySelector(classname).style.background = 'conic-gradient(' + colorname + ' 0% ' + i + '%, #E84057 ' + i + '% 100%)';
}
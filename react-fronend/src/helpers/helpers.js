export const isObjectEmpty = (obj) => {
    return Object.keys(obj).length === 0 && obj.constructor === Object;
}

/**
 * Convert a element to a plain text and then download
 * @param {*} filename 
 * @param {*} text 
 */
export const downloadTextAsFile = (filename, text) => {
    let element = document.createElement('a');
    element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
    element.setAttribute('download', filename + '.txt'); // Agrega la extensión .txt al nombre del archivo

    element.style.display = 'none';
    document.body.appendChild(element);

    element.click();

    document.body.removeChild(element)
}
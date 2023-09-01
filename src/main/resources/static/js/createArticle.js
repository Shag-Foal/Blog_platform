const dfreeHeaderConfig = {
    selector: '.dfree-header',
    menubar: false,
    inline: true,
    toolbar: false,
    plugins: ['quickbars'],
    quickbars_insert_toolbar: 'undo redo',
    quickbars_selection_toolbar: 'italic underline',
};

const dfreeBodyConfig = {
    selector: '.dfree-body',
    menubar: false,
    inline: true,
    plugins: [
        'autolink',
        'codesample',
        'link',
        'lists',
        'media',
        'powerpaste',
        'table',
        'image',
        'quickbars',
        'codesample',
        'help'
    ],
    toolbar: false,
    quickbars_insert_toolbar: 'quicktable image media codesample',
    quickbars_selection_toolbar: 'bold italic underline | formatselect | blockquote quicklink',
    contextmenu: 'undo redo | inserttable | cell row column deletetable | help',
    powerpaste_word_import: 'clean',
    powerpaste_html_import: 'clean',
};

tinymce.init(dfreeHeaderConfig);
tinymce.init(dfreeBodyConfig);
const imageInput = document.getElementById('images');
const imagePreviewContainer = document.getElementById('image-preview-container');

imageInput.addEventListener('change', function () {
    imagePreviewContainer.innerHTML = '';
    for (let i = 0; i < imageInput.files.length; i++) {
        const file = imageInput.files[i];
        if (file.type.match('image.*')) {
            const img = document.createElement('img');
            img.src = URL.createObjectURL(file);
            img.classList.add('preview-image');
            imagePreviewContainer.appendChild(img);
        }
    }
});


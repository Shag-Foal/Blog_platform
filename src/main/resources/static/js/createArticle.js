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
            img.src = URL.createObjectURL(file);//
            img.classList.add('preview-image');
            imagePreviewContainer.appendChild(img);
        }
    }
});

const form = document.getElementById('form')
let article = {
    content:'',
    publishDate: '',
    preview:'',
    title:''
}
const previewArticle = document.getElementById('previewArticle');

previewArticle.addEventListener('click', (event) => {
    event.preventDefault();
    article.content = document.getElementById('editor').innerHTML;
    // article.hashtags = document.getElementById('hashtags').value.split(/[ ,]+/).filter(part => part.trim() !== '');
    article.title = document.getElementById('title').value;
    const formData = new FormData(form);
    console.log('Метод зароботал')

    fetch('/uploadImage', {
        method: 'POST',
        body: formData,
    })
        .then(response => response.text())
        .then(data => {
          article.preview = data
            fetch('/previewArticle', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(article)
            })
                .then(response => {
                    if (response.ok){
                        location.href = '/previewArticle';
                    }
                    else {
                        location.href = '/login';
                    }
                })
                .catch(error => {
                    console.error('Ошибка при отправке запроса', error);
                });
          // localStorage.setItem('content',article.content);
          // localStorage.setItem('title',article.title);
          // localStorage.setItem('preview',article.preview);
          // localStorage.setItem('publishDate',article.publishDate);
          // const params = new URLSearchParams(article)
          // location.href = '/previewArticle';
        })
        .catch(error => console.error('Ошибка при загрузке изображения', error));
});
form.addEventListener('submit', (event) => {
    event.preventDefault();
    article.content = document.getElementById('editor').innerHTML;
    // article.hashtags = document.getElementById('hashtags').value.split(/[ ,]+/).filter(part => part.trim() !== '');
    article.title = document.getElementById('title').value;
    const formData = new FormData(form);
    fetch('/uploadImage', {
        method: 'POST',
        body: formData,
    })
        .then(response => response.text())
        .then(data => {
            article.preview = data;

            fetch('/newArticle', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(article),
            })
                .then(response => {
                    if (response.ok) {
                        window.location.href = 'http://localhost:8080';
                    } else {
                        console.log('Ошибка при отправке');
                    }
                })
                .catch(e => console.log('Ошибка' + e));
        })
        .catch(error => console.error('Ошибка при загрузке изображения', error));
});

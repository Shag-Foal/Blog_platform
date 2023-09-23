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
const changePreviewButton = document.querySelector('.file-input-button');
const imageInput = document.getElementById('images');

changePreviewButton.addEventListener('click', function () {
    imageInput.click();
});
const imagePreviewContainer = document.getElementById('image-preview-container');
imageInput.addEventListener('change', () => {
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
    title:'',
    username:'',
    id:''
}
const previewArticle = document.getElementById('previewArticle');

previewArticle.addEventListener('click', (event) => {
    event.preventDefault();
    article.content = document.getElementById('editor').innerHTML;
    article.username = document.getElementById('author').value;
    article.publishDate = document.getElementById('publishDate');
    // article.hashtags = document.getElementById('hashtags').value.split(/[ ,]+/).filter(part => part.trim() !== '');
    article.title = document.getElementById('title').value;
    const formData = new FormData(form);
    console.log('Метод зароботал')
    article.id = document.getElementById('id').value;
    if (document.getElementById('preview-image') == null) {
        fetch('/uploadImage', {
            method: 'POST',
            body: formData,
        })
            .then(response => response.text())
            .then(data => {
                article.preview = data;

                fetch('/previewArticle', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(article)
                })
                    .then(response => {
                        if (response.ok) {
                            localStorage.setItem('id', article.id)
                            location.href = '/previewArticle';
                        } else {
                            location.href = '/login';
                        }
                    })
                    .catch(error => {
                        console.error('Ошибка при отправке запроса', error);
                    });
            })
            .catch(error => console.error('Ошибка при загрузке изображения', error));
    }
    else {
        article.preview = document.getElementById('preview-image')
        article.preview = article.preview.src
        localStorage.setItem('article', '1')
        fetch('/previewArticle', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(article)
        })
            .then(response => {
                if (response.ok) {
                    localStorage.setItem('id', article.id)
                    location.href = '/previewArticle';
                } else {
                    location.href = '/login';
                }
            })
            .catch(error => {
                console.error('Ошибка при отправке запроса', error);
            });

    }
});
form.addEventListener('submit', (event) => {
    event.preventDefault();
    article.content = document.getElementById('editor').innerHTML;
    article.username = document.getElementById('author').value;
    article.publishDate = document.getElementById('publishDate').value;
    // article.hashtags = document.getElementById('hashtags').value.split(/[ ,]+/).filter(part => part.trim() !== '');
    article.title = document.getElementById('title').value;
    article.id = document.getElementById('id').value;
    const formData = new FormData(form);
    console.log(JSON.stringify(article))
    if (document.getElementById('preview-image') == null) {
    fetch('/uploadImage', {
        method: 'POST',
        body: formData,
    })
        .then(response => response.text())
        .then(data => {
            article.preview = data;

            fetch('/editArticle', {
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
    }
    else {
        const fullImageUrl = document.getElementById('preview-image').src;
        const domain = window.location.protocol + '//' + window.location.host; // Получаем домен и протокол текущей страницы
        article.preview = fullImageUrl.replace(domain, '');
        fetch('/editArticle', {
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
    }
});

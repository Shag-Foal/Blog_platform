'use strict'
function handleLike(articleID) {
    fetch(`/${articleID}/like`, {
        method: 'POST',
        credentials: 'include',
    })
        .then(response => response.text())
        .then(data => {
            if (data === 'Liked') {
                const likesCount = document.getElementById('likesCount');
                likesCount.textContent = parseInt(likesCount.textContent) + 1;
            } else if (data === 'removeLike') {
                const likesCount = document.getElementById('likesCount');
                likesCount.textContent = parseInt(likesCount.textContent) - 1;            }
            else if (data==='Swapped'){
                const likesCount = document.getElementById('likesCount');
                likesCount.textContent = parseInt(likesCount.textContent) + 1;
                const dislikesCount = document.getElementById('dislikesCount');
                dislikesCount.textContent = parseInt(dislikesCount.textContent) -1;
            }
        })
        .catch(error => {
            console.error('Произошла ошибка:', error);
        });
}

function handleDislike(articleID) {
    fetch(`/${articleID}/dislike`, {
        method: 'POST',
        credentials: 'include',
    })
        .then(response => response.text())
        .then(data => {
            if (data === 'Disliked') {
                const dislikesCount = document.getElementById('dislikesCount');
                dislikesCount.textContent = parseInt(dislikesCount.textContent) + 1;
            } else if (data === 'removeDislike') {
                const dislikesCount = document.getElementById('dislikesCount');
                dislikesCount.textContent = parseInt(dislikesCount.textContent) - 1;            }
            else if (data==='Swapped'){
                const likesCount = document.getElementById('likesCount');
                likesCount.textContent = parseInt(likesCount.textContent) - 1;
                const dislikesCount = document.getElementById('dislikesCount');
                dislikesCount.textContent = parseInt(dislikesCount.textContent) + 1;
            }
        })
        .catch(error => {
            console.error('Произошла ошибка:', error);
        });
}
const commentForm = document.getElementById('commentForm');
const comment = {
    content:'',
    article:''

}
commentForm.addEventListener('submit', function (event) {
    event.preventDefault();
    comment.content = document.getElementById('comment').value;
    comment.article = document.getElementById('articleId').textContent;
    fetch('/postComment',{
        method:'Post',
        headers: {
            'Content-Type': 'application/json',
        },
        body:JSON.stringify(comment)
    }).then(response =>{
            if (response.ok){
                console.log('Комментарий успешно отправлен');
                location.reload();
            }
            else{
                console.log('Ошибка при отправке')
            }
        }
    ).catch(e => console.log('Ошибка ' + e))
});

function commentLike(Id){
    fetch(`/${Id}/commentLike`, {
        method: 'POST',
        credentials: 'include'
    }).then(response => response.text())
        .then(data => {
            if (data === 'liked'){
                const commentLikes = document.getElementById('commentLikes');
                commentLikes.textContent = parseInt(commentLikes.textContent) + 1;
            }
            else if (data === 'already liked'){
                const commentLikes = document.getElementById('commentLikes');
                commentLikes.textContent = parseInt(commentLikes.textContent) - 1;            }
            else if (data === 'not authenticated'){
                window.location.href = 'http://localhost:8080/login';
            }
        }).catch(e => console.log(e));
}
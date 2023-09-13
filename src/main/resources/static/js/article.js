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
const commentReplyForm = document.getElementById('commentReplyForm');
const comment = {
    content:'',
    article:''

}
const commentReply = {
    content:'',
    article:'',
    replyUser:''
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
commentReplyForm.addEventListener('submit', function (event) {
    event.preventDefault();
    commentReply.content = document.getElementById('comment').value;
    commentReply.article = document.getElementById('articleId').textContent;
    commentReply.replyUser = document.getElementById('parentCommentId').textContent;

    fetch('/postCommentReply',{
        method:'Post',
        headers: {
            'Content-Type': 'application/json',
        },
        body:JSON.stringify(commentReply)
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



function commentLike(event, commentId){
    fetch(`/${commentId}/commentLike`, {
        method: 'POST',
        credentials: 'include'
    }).then(response => response.text())
        .then(data => {
            const commentLikes = event.target.parentElement.querySelector('#commentLikes');
            if (data === 'liked'){
                commentLikes.textContent = parseInt(commentLikes.textContent) + 1;
            }
            else if (data === 'already liked'){
                commentLikes.textContent = parseInt(commentLikes.textContent) - 1;
            }
            else if (data === 'not authenticated'){
                window.location.href = 'http://localhost:8080/login';
            }
        }).catch(e => console.log(e));
}

const replyButtons = document.querySelectorAll('.reply-button');

replyButtons.forEach(button => {
    button.addEventListener('click', function () {
        const commentId = button.getAttribute('data-comment-id');

        const replyForm = document.createElement('form');
        replyForm.innerHTML = `
        <form id="commentReplyForm" class="comment-meta">
            <label for="replyComment">Ваш ответ:</label><br>
            <textarea class="comment-content" id="replyComment" name="replyComment" rows="4" cols="50" required></textarea><br>
            <input id="parentCommentId" type="hidden" name="parentCommentId" value="${commentId}">
            <input class="hover_button" type="submit" value="Отправить"></form>
        `;

        const replyFormContainer = document.getElementById('replyFormContainer');

        replyFormContainer.innerHTML = '';
        replyFormContainer.appendChild(replyForm);
    });
});

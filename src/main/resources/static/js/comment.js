const commentCreateButton = document.getElementById('commentCreate-btn');
const commentContent      = document.getElementById('commentContent');
const token               = localStorage.getItem('access_token');

function layerPop(popName){
	var $layer = $("#"+ popName);
	$layer.fadeIn(500).css('display', 'inline-block').wrap( '<div class="overlay_t"></div>');
	$('body').css('overflow','hidden');
}
function layerPopClose(){
	$(".popLayer").hide().unwrap( '');
	$('body').css('overflow','auto');
	$(".popLayer video").each(function() { this.pause(); this.load(); });
}
function layerPopClose2(popName){
	$("#"+ popName).hide().unwrap( '');
	$('body').css('overflow','auto');
}

function openCommentUpdatePopup(id) {

    const articleId = document.getElementById('article-id').value;

    $.ajax({
        url: `/api/articles/${articleId}/comments/${id}`,
        type: 'GET',
        dataType: 'JSON',
        beforeSend(xhr) {
                    if (token) {
                        xhr.setRequestHeader('Authorization', 'Bearer ' + token);
                    }
                },
        async: false,
        success: function (response) {
            document.getElementById('modalWriter').value = response.author;
            document.getElementById('modalContent').value = response.commentContent;
            document.getElementById('commentUpdateBtn').setAttribute('onclick', `updateComment(${id})`);
            layerPop('commentUpdatePopup');
        },
        error: function (request, status, error) {
            console.log(error)
        }
    })
}

function closeCommentUpdatePopup(id) {
    document.querySelectorAll('#modalContent, #modalWriter').forEach(element => element.value = '');
    document.getElementById('commentUpdateBtn').removeAttribute('onclick');
    layerPopClose('commentUpdatePopup');
}

function updateComment(id) {

    const writer = document.getElementById('modalWriter');
    const content = document.getElementById('modalContent');

    const articleId = document.getElementById('article-id').value;
    const params = {
        id: id,
        articleId: document.getElementById('article-id').value,
        commentContent: commentContent,
        author: author
    }

    $.ajax({
        url: `/api/articles/${articleId}/comments/${id}`,
        type: 'patch',
        contentType : 'application/json; charset=utf-8',
        dataType: 'json',
        data: JSON.stringify(params),
        async: false,
        success: function(response) {
            alert('수정되었습니다.');
            closeCommentUpdatePopup;
            findAllComment();
        },
        error: function(request, status, error) {
            console.log(error)
        }
    })
}

// 댓글 목록 조회 함수
function findAllComment() {
    const articleId = document.getElementById('article-id').value;

    $.ajax({
        url: `/api/articles/${articleId}/comments`,
        type: 'GET',
        dataType: 'json',
        beforeSend(xhr) {
            if (token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + token);
            }
        },
        success(response) {
            const listEl = document.querySelector('.comment_list');
            if (!response.length) {
                listEl.innerHTML =
                    '<div class="comment_none"><p>등록된 댓글이 없습니다.</p></div>';
                return;
            }

            let commentHtml = '';
            response.forEach(row => {
                commentHtml += `
                    <div>
                        <span class="writer_img">
                            <img src="/img/google.png" width="30" height="30" alt="기본 프로필 이미지"/>
                        </span>
                        <p class="writer">
                            <em>${row.author}</em>
                            <span class="date">${dayjs(row.createdDate).format('YYYY-MM-DD HH:mm')}</span>
                        </p>
                        <div class="content"><div class="txt.con">${row.commentContent}</div></div>
                        <p class="func_btn">
                            <button type="button" onclick="openCommentUpdatePopup(${row.id})" class="btn">
                                <span class="icons icon_modify">수정</span>
                            </button>
                            <button type="button" class="btn">
                                <span class="icons icon_del">삭제</span>
                            </button>
                        </p>
                    </div>
                `;
            });
            listEl.innerHTML = commentHtml;
        },
        error(_, status, err) {
            console.error('댓글 조회 실패:', status, err);
            if (status === '401') {
                alert('로그인이 필요합니다.');
            }
        }
    });
}

// 댓글 등록 이벤트
if (commentCreateButton) {
    commentCreateButton.addEventListener('click', () => {
        const content = commentContent.value.trim();
        if (!content) {
            alert('댓글 내용을 입력해주세요.');
            return;
        }

        const articleId = document.getElementById('article-id').value;
        if (!articleId) {
            alert('게시글 ID를 찾을 수 없습니다.');
            return;
        }

        const params = {
            articleId: document.getElementById('article-id').value,
            commentContent: content
        };

        $.ajax({
            url: `/api/articles/${articleId}/comments`,
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: JSON.stringify(params),
            beforeSend(xhr) {
                        if (token) {
                            xhr.setRequestHeader('Authorization', 'Bearer ' + token);
                        }
                    },
            async: true,
            success(response) {
                console.log('댓글 등록 성공:', response);
                alert('댓글이 등록되었습니다.');
                // commentContent.value = '';
                // 필요시 댓글 목록 새로고침
                findAllComment();
            },
            error(xhr, status, error) {
                console.error('댓글 등록 실패:', status, error, xhr.responseText);
                alert('댓글 등록에 실패했습니다. 다시 시도해주세요.');
            }
        });
    });
}

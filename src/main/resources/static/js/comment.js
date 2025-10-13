const commentCreateButton = document.getElementById('commentCreate-btn');
const commentContent      = document.getElementById('commentContent');
const token               = localStorage.getItem('access_token');

$(".comment_list > div > a").click(function(){
		var submenu = $(this).next("div.hide_view");
		if( submenu.is(":visible") ){
			submenu.removeClass("open");
		}else{
			submenu.addClass("open");
		}
	});

function layerPop(popName){
    var $layer = $("#" + popName);

    // 이미 오버레이로 감싸져 있지 않은 경우에만 wrap
    if (!$layer.parent().hasClass('overlay_t')) {
        $layer.wrap('<div class="overlay_t"></div>');
    }

    // fadeIn 완료 후 display 변경
    $layer.fadeIn(500, function() {
        $(this).css('display', 'inline-block');
    });

    $('body').css('overflow', 'hidden');
}

function layerPopClose(){
	$(".popLayer").hide().unwrap( '');
	$('body').css('overflow','auto');
	$(".popLayer video").each(function() { this.pause(); this.load(); });
}
function layerPopClose2(popName){
    $("#" + popName).fadeOut(300, function() {
        if ($(this).parent().hasClass('overlay_t')) {
            $(this).unwrap(); // 매개변수 제거
        }
    });
    $('body').css('overflow', 'auto');
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
        // id : id,
        // articleId : articleId,
        commentContent : content.value,
        // author : writer.value
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
            closeCommentUpdatePopup();
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
                            <button type="button" onclick="deleteComment(${row.id})"class="btn">
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

function deleteComment(id) {

    if ( !confirm('선택하신 댓글을 삭제할까요?') ) {
        return false;
    }

    const articleId = document.getElementById('article-id').value;

    $.ajax({
        url : `/api/articles/${articleId}/comments/${id}`,
        type : 'delete',
        // dataType : 'json',
        async : false,
        success : function (response) {
            alert('삭제되었습니다.');
            findAllComment();
        },
        error : function (request, status, error) {
            console.log(error)
        }
    })
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

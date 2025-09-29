const commentCreateButton = document.getElementById('commentCreate-btn');
const commentContent = document.getElementById('commentContent');

// 쿠키 또는 로컬스토리지에서 토큰 읽기
const token = localStorage.getItem('access_token');  // 로그인 시 저장했다고 가정

// isValid 함수가 정의되어 있다고 가정
// isValid(commentContent, '댓글');

if (commentCreateButton) {
    commentCreateButton.addEventListener("click", () => {
        // 입력값 유효성 검사
        if (!document.getElementById("commentContent").value.trim()) {
            alert('댓글 내용을 입력해주세요.');
            return;
        }

        let articleId = document.getElementById("article-id").value;

        // articleId 유효성 검사
        if (!articleId) {
            alert('게시글 ID를 찾을 수 없습니다.');
            return;
        }

        // ✅ 객체 리터럴 문법 수정 (= → :)
        const params = {
            articleId: document.getElementById("article-id").value,
            commentContent: document.getElementById("commentContent").value,
        };

        // ✅ AJAX 요청 (콤마 누락 수정)
        $.ajax({
            url: `/api/articles/${articleId}/comments`,
            type: 'POST',  // 대문자 권장
            contentType: 'application/json; charset=utf-8',  // ✅ 콤마 추가
            dataType: 'json',
            data: JSON.stringify(params),
            async: true,  // ✅ 비동기로 변경 권장
            success: function (response) {
                console.log('댓글 등록 성공:', response);
                // ✅ 성공 시 추가 처리
                alert('댓글이 등록되었습니다.');
                // commentContent.value = '';  // 입력란 초기화
                // 댓글 목록 새로고침 등 추가 작업
            },
            error: function (xhr, status, error) {  // ✅ 매개변수명 명확화
                console.error('댓글 등록 실패:', error);
                console.error('상태:', status);
                console.error('응답:', xhr.responseText);
                alert('댓글 등록에 실패했습니다. 다시 시도해주세요.');
            }
        });
    }); // ✅ 세미콜론 추가
}

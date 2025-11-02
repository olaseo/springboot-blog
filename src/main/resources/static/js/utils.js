// js/utils.js
// ——————————————————————————————————————————
// 쿠키에서 이름에 해당하는 값 꺼내는 헬퍼
function getCookie(name) {
  const match = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'));
  return match ? match[2] : null;
}

// 페이지 로드 시 전역 AJAX 설정
$(function() {
  const token = localStorage.getItem('access_token');
  console.log('utils.js 실행, 저장된 access_token:', token);

  if (token) {
    $.ajaxSetup({
      beforeSend(xhr) {
        console.log('beforeSend 호출, Authorization 헤더 설정');
        xhr.setRequestHeader('Authorization', 'Bearer ' + token);
      }
    });
    console.log('Authorization 헤더 설정 완료');
  } else {
    console.warn('access_token 없음: Authorization 헤더를 설정할 수 없습니다.');
  }
});

function ajaxRequest(method, url, data, success, fail) {
  $.ajax({
    method: method,
    url: url,
    contentType: 'application/json; charset=UTF-8',
    dataType: 'json',
    data: data,
    beforeSend(xhr) {
      const token = localStorage.getItem('access_token');
      if (token) {
        console.log('ajaxRequest.beforeSend, 토큰:', token);
        xhr.setRequestHeader('Authorization', 'Bearer ' + token);
      }
    },
    success(response) {
      success(response);
    },
    error(xhr) {
      // 401 Unauthorized + 리프레시 토큰 있으면 재발급 시도
      if (xhr.status === 401) {
        const refreshToken = getCookie('refresh_token');
        if (refreshToken) {
          console.log('401 발생, 리프레시 토큰으로 재발급 시도');
          return $.ajax({
            method: 'POST',
            url: '/api/token',
            contentType: 'application/json; charset=UTF-8',
            dataType: 'json',
            data: JSON.stringify({ refreshToken: refreshToken }),
            success(res) {
              console.log('새 액세스 토큰:', res.accessToken);
              localStorage.setItem('access_token', res.accessToken);
              // 원래 요청 재시도
              ajaxRequest(method, url, data, success, fail);
            },
            error() {
              console.error('토큰 재발급 실패');
              fail(xhr);
            }
          });
        }
      }
      // 그 외의 경우 실패 콜백
      fail(xhr);
    }
  });
}

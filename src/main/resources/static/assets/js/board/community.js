import { addModalBtnEvent, addModalCloseEvent } from "../common/modal-handler.js";

// 게시글 쓰기
(() => {
  console.log("daaa");
  addModalBtnEvent();
  addModalCloseEvent();
})();




//document.getElementById("search").addEventListener("click", handleSearchClick);
//
//document.getElementById("category-btn").addEventListener("click", function (event) {
//  // 클릭 이벤트 처리 코드 작성
//  console.log("Button clicked:", event.target.id);
//});

//document.getElementById("freedom-btn").addEventListener("click", function () {
//  // 클릭 이벤트 처리 코드 작성
//  console.log("Freedom button clicked!");
//});
//
//document.getElementById("accuse-btn").addEventListener("click", function () {
//  // 클릭 이벤트 처리 코드 작성
//  console.log("Accuse button clicked!");
//});
window.handleSearchClick = function() {
  const baseUrl = 'http://localhost:8282';
  const page = 1; // 페이지 번호
  const keyword = document.getElementById('search').value; // 검색 키워드 값 가져오기
  const boardCategory = document.getElementById('category-btn').value; // 게시판 카테고리 값 가져오기

  fetch(`${baseUrl}/api/v1/boards/${page}/${keyword}/${boardCategory}`)
      .then(response => {
        if (response.ok) {
          return response.json();
        } else {
          throw new Error('게시글 목록을 가져오지 못했습니다');
        }
      })
      .then(boardList => {
        console.log(boardList); // 가져온 게시글 목록 출력
        // 추가로 처리할 로직 작성
      })
      .catch(error => {
        console.error(error);
        // 오류 처리 로직 작성
      });
  }

document.getElementById("search").addEventListener("click", handleSearchClick);

document.getElementById("category-btn").addEventListener("click", function (event) {
  // 클릭 이벤트 처리 코드 작성
  console.log("Button clicked:", event.target.id);
});

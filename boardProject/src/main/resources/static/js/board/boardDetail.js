/* 좋아요 버튼(하트) 클릭 시 비동기로 좋아요 INSERT/DELETE */

// 1) 로그인한 회원 번호 준비
//      --> session에서 얻어오기
//          (session은 서버에서 관리하기 떄문에 JS로 없음)  
// 2) 현재 게시글 번호 준비
// 3) 좋아요 여부 준비 

// 1. #boardLike가 클릭 되었을 때
const boardLike = document.querySelector("#boardLike");
boardLike.addEventListener("click",e=>{
    //2. 로그인 상태가 아닌 경우 동작 X
    if(loginMemberNo == null){
        alert("로그인 후 이용해 주세요");
        return;
    }
    
    //주닙된 3개의 변수를 객체로 저장 ( JSON 변환 예정 )
const obj ={
    "memberNo" : loginMemberNo,
    "boardNo" : boardNo,
    "likeCheck" : likeCheck
};

// 4> 좋아요 INSERT/DELETE 비동기 요청
fetch("/board/like",{
    method : "POST",
    headers : {"Content-Type":"application/json"},
    body : JSON.stringify(obj)
})
.then(resp=>resp.text())
.then(count=>{
    // count == 첫 번째 then의 파싱되어 반환된 값('-1' 또는 '게시글 좋아요 수')
    // console.log("count : ", count);

    
    if(count ==-1){
        console.log("좋아요 처리 실패");
        return;
    }
    // 5. likeCheck 값 0 <-> 1 변환
    // (왜? 클릭 될 때 마다 INSERT/DELETE 동작을 번갈아 가면서 할 수 있다)
    likeCheck = likeCheck == 0 ? 1 : 0;
    
    // 6. 하트를 채웠다/비웠다 바꾸기
    e.target.classList.toggle("fa-regular");
    e.target.classList.toggle("fa-solid");

    // 7. 게시글 좋아요 수 변경
    e.target.nextElementSibling.innerText = count;




});



});

// Thymeleaf(Template Engine : 화면을 쉽게 만들어주는 도구) :
// 자바에서 화면 만들기 어려우니까 쉽게 만들어주는 도구 (표기 : HTML, 해석 : Java)
// Thymeleaf는
// - html 코드 (+css, js)
// - th : 코드(java) + Spring EL

// Thymeleaf 코드 해석 순서
// 1. th : 코드(java) + Spring EL
// 2. html 코드 (+css, js)


// Sevlet -> 요청 받으면 응답 화면 만들어서 출력 





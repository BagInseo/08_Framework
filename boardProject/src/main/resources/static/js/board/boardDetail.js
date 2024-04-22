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





/* const deleteBtn = document.querySelector("#deleteBtn"); //삭제 버튼

deleteBtn.addEventListener("click", e=>{
    
    if(!confirm("삭제 하시겠습니까?")){ //취소 버튼눌렀을 때
        alert("취소됨");
        e.preventDefault;
        return;

    } else{
        
        const deleteBtnForm = document.querySelector("#deleteBtnForm");
        deleteBtnForm.submit();
    }
}) */


/* 삭제(GET) */
const deleteBtn = document.querySelector("#deleteBtn");

if(deleteBtn != null){
  deleteBtn.addEventListener("click", () => {
    if( !confirm("삭제 하시겠습니까?") ) {
      alert("취소됨")
      return;
    }

    const url = location.pathname.replace("board","editBoard") + "/delete"; // /editBoard/1/2000/delete
    const queryString = location.search; // ? cp=1
    location.href = url + queryString;
  });
}


/* 삭제(POST) */
const deleteBtn2 = document.querySelector("#deleteBtn2");

if(deleteBtn2 != null){
  deleteBtn2.addEventListener("click", () => {

    if( !confirm("삭제 하시겠습니까?") ) {
      alert("취소됨")
      return;
    }

    const url = location.pathname.replace("board","editBoard")  + "/delete"; 

    // form태그 생성
    const form = document.querySelector("form");
    form.action = url;
    form.method = "POST";

    // cp값을 저장할 input 생성
    const input = document.querySelector("input");
    input.type = "hidden";
    input.name = "cp";

    // 쿼리스트링에서 원하는 파라미터 얻어오기
    const params = new URLSearchParams(location.search)
    const cp = params.get("cp");
    input.value = cp;

    form.append(input);

    // 화면에 form태그를 추가한 후 제출하기
    document.querySelector("body").append(form);
    form.submit();
  });
}



/* -------------------게시글 수정 버튼 ------------------------ */
const updateBtn= document.querySelector("#updateBtn");

if(updateBtn!=null){ //수정 버튼 존재 시
    updateBtn.addEventListener("click",()=>{

        // 현재 : /board/1/2010?cp=1
        // 목표 : /editBOard/1/2010/update?cp=1 (GET방식)
        location.href= location.pathname.replace('board','editBoard')
                        +"/update"
                        +location.search
    })
}



//------------------------------------
/* 목록으로 졸아가는 버튼 */
const goToListBtn = document.querySelector("#goToListBtn");

goToListBtn.addEventListener("click",()=>{
  let url = location.pathname;
  url = url.substring(0,url.lastIndexOf("/"));
  
  location.href=url + location.search; 
                      // 쿼리 스트링 부분
})




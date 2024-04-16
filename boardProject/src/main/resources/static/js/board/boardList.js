
/* 글쓰기 버튼 클릭 시 */
const insertBtn = document.querySelector("#insertBtn");

if(insertBtn!=null){
    //글쓰기 버튼이 존재할 때
    insertBtn.addEventListener("click",()=>{
    
        /* boardCode 얻어오느 방법 */
        // 1) @PathVariable("boardCode") 얻어와 전역 변수 저장
        // 2) location.pathname.split("/")[2]
    
        //get방식 요청
        location.href= `/editBoard/${boardCode}/insert`;

    })
}


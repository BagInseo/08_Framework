const totalCount = document.querySelector("#totalCount");
const completeCount = document.querySelector("#completeCount");
const reloadBtn = document.querySelector("#reloadBtn");


//할 일 추가 관련 요소
const todoTitle = document.querySelector("#todoTitle");
const todoContent = document.querySelector("#todoContent");
const addBtn = document.querySelector("#addBtn");

// 할 일 목록 조회
const tbody = document.querySelector("#tbody");

// 할 일 상세 조회 관련 요소
const popupLayer = document.querySelector("#popupLayer");
const popupTodoNo = document.querySelector("#popupTodoNo");
const popupTodoTitle = document.querySelector("#popupTodoTitle");
const popupComplete = document.querySelector("#popupComplete");
const popupRegDate = document.querySelector("#popupRegDate");
const popupTodoContent = document.querySelector("#popupTodoContent");
const popupClose = document.querySelector("#popupClose")


// 상세 조회 버튼
const deleteBtn = document.querySelector("#deleteBtn");
const completeBtn= document.querySelector("#completeBtn");
const updateView = document.querySelector("#updateView");



// 수정 레이어 버튼
const updateLayer = document.querySelector("#updateLayer");
const updateTitle = document.querySelector("#updateTitle");
const updateContent = document.querySelector("#updateContent");
const updateBtn = document.querySelector("#updateBtn");
const viewClose = document.querySelector("#viewClose");

// -----------

//전체 Todo 개수 조회 및 출력하는 함수
function getTotalCount(){

    //비동기로 서버(DB)에서 전체 Todo 개수 조회하는 
    //fetch() API 코드 작성
    //(fetch : 가지고 오다)

    fetch("/ajax/totalCount") //비동기 요청 수행 -> promise 객체 반환
    .then(response =>{

        //response : 비동기 요청에 대한 응답이 담긴 객체

        //response.text() : 응답 데이터를 문자열/숫자 형태로 변환한 
        //                   결과를 가지는 Promise 객체 반환
        console.log(response);
        //console.log(response.text());

        return response.text();
    })

    //두 번째 then의 매개 변수 (result)
    // == 첫 번재 then에서 반환된 Promise 객체의 PromiseResult 값
    .then(result =>{

        //result 매개 변수 == Controller 메서드에서 반환된 값
        console.log("result", result);
        totalCount.innerHTML=result;
    })

    //totalCount의 요소의 내용을 20으로 변홀
   

}//함수 호출



//completeCount 값 비동기 통신으로 얻어와서 화면 출력
function getCompleteCount(){

    //fetch : 비동기로 요청해서 결과 데이터 가져오기

    //첫 번재 then의 response : 
    //  - 응답 결과 , 요청 주소 , 응답 데이터 등이 담겨있음

    //response.text() : 응답 데이터를 text 형태로 변환
    
    //두 번쨰 then의 result : 
    //  - 첫 번째 then에서 text로 변환된 응답 데이터(completeCount값)
    fetch("/ajax/completeCount")
    .then(response=>response.text())
    .then(result => {

        // #completeCount 요소에 내용으로 result값 출력
        completeCount.innerText=result;
    })
}



// 새로고침 버튼이 클릭 되었을 때
reloadBtn.addEventListener("click", ()=>{
    getTotalCount(); //비동기로 전체 할 일 개수 조회
    getCompleteCount();  //비동기로 완료된 할 일 개수 조회
})


//-------------------------------------------------------------------------
// 할 일 추가 버튼 클릭 시 등록
addBtn.addEventListener("click",()=>{

    //비동기로 할 일 추가하는 fetch( )API 코드 작성
    //  - 요청 주소 : "/ajax/add


    //파라미터를 저장한 JS 객체 
    const param = {
        "todoTitle" : todoTitle.value,
        "todoContent" : todoContent.value
    
    }

    fetch("/ajax/add",{
        method : "POST", //POST 방식 요청
        headers : {"Content-Type" : "application/json"}, //요청 데이터의 형식을 json으로 지정
        body : JSON.stringify(param)  //param 객체를 JSON(String)으로 변환
    })
    .then(resp=>resp.text())  //반환된 값을 text로 변환
    
    // 첫번째 then에서 반환된 값 중 변환된 text를 temp에 저장
    .then(temp=> {
        if(temp>0) {
            alert("추가 성공!!");

            //추가 성공한 제목, 내용 지우기
            todoTitle.value="";
            todoContent.value="";

            //할 일이 추가되었기 떄문에 전체 Todo 개수 다시 조회
            getTotalCount();

            //할일 목록 다시 조회
            selectTodoList();
        }
        else{
            alert("추가 실패..");
        }
    })
})





//-------------------------------------------------------------------------

//비동기로 할 일 상세 조회하는 함수
const selectTodo = (url) => {

    //매개변수 url == "/ajax/detail?todoNo-10" 형태의 문자열
    fetch(url)

    //response.json() : 응답 데이터가 JSON인 경우 
    //                  이를 자동으로 Object 형태로 변환하는 메서드
    //         == JSON.parse (JSON데이터)

    .then(response => response.json())
    .then(todo=>{
        //매개 변수 todo : 서버 응답(JSON)이 Object로 변환된 값
        //(첫 번째 then 반환 결과)

        console.log(todo);

        //popupLayer에 조회된 값을 출력
        popupTodoNo.innerText=todo.todoNo;
        popupTodoTitle.innerText=todo.todoTitle;
        popupComplete.innerText=todo.complete;
        popupRegDate.innerText=todo.regDate;
        popupTodoContent.innerText=todo.todoContent;


        //popupLayer 보이게하기
        popupLayer.classList.remove("popup-hidden");

        //update layer가 혹시라도 열려있으면 숨기기
        updateLayer.classList.add("popup-hidden");

        /* 요소.classList.toggle("클래스명")
        - 요소에 해당 클래스가 있으면 제거
        - 요소에 해당 클래스가 없으면 추가

        /* 요소.classList.add("클래스명")
        - 요소에 해당 클래스가 없으면 추가

        /* 요소.classList.remove("클래스명")
        - 요소에 해당 클래스가 없으면 추가
        */

    })

};


//-------------------------------------------------------------------------

//비동기(ajax)로 할 일 목록을 조회하는 함수
const selectTodoList= ()=>{

    fetch("/ajax/selectList") 

    .then(response => response.text())  //응답 결과를 text로 변환
    .then(result =>{//result == 첫 번째 then에서 반환된 결과값
        //console.log(result);
        console.log(typeof result);

        //JSON은 객체가 아니라 문자열!!!
        //문자열은 가공할 수 잆는데 어렵다
        //JSON.parse(JSON데이터) 이용

        //JSON.parse(JSON데이터) :
        // - string 형태의 JSON데이터를 JS Object 타입으로 변환

          //JSON.stringify(JSON데이터) :
        // - JSON데이터를  string 형태의 JS Object 타입으로 변환
        
        const todoList = JSON.parse(result);
        console.log(todoList);//객테 배열 형태 확인

        //------------------------------------------
        
        //기존에 출력되어 있던 할 일 목록을 모두 삭제
        tbody.innerHTML="";
        
        //------------------------------------------


        //#tbody에 tr/td 요소를 생성해서 내용 추가
        for(let todo of todoList){
            
            //tr생성
            const tr= document.createElement("tr");

            const arr =['todoNo','todoTitle','complete',"regDate"];

            for(let key of arr){
                const td = document.createElement("td");

                if (key==='todoTitle'){
                    const a = document.createElement("a");
                    a.innerText=todo[key];
                    a.href="/ajax/detail?todoNo="+ todo.todoNo;
                    td.append(a);
                    tr.append(td);


                    //a태그 클릭 시 기본 이벤트 막기
                    a.addEventListener("click",e=> {
                        e.preventDefault();
                    

                        //할 일 상세조회 비동기 요청
                        //e.target.href : 클릭된 a태그의 href 속성 값
                        selectTodo(e.target.href);

                    });
                    continue;
                }

                td.innerText=todo[key];
                tr.append(td);
            }

            tbody.append(tr);
        }
    })

};





//-------------------------------------------------------------------------

//popupLayer의 X버튼 클릭 시 닫기
popupClose.addEventListener("click", ()=>{
    //숨기는 클래스 추가
    popupLayer.classList.add("popup-hidden");
});

//-------------------------------------------------------------------------
// 삭제 버튼 클릭 시
deleteBtn.addEventListener("click",()=>{

    // 취소 클릭 시 아무것도 안함
    if(!confirm("정말 삭제 하시겠습니까?")){return;}

    //삭제할 할 일 번호(PK) 얻어오기
    const todoNo = popupTodoNo.innerText;  //#popupTodoNo 얻어오기

    //비동기 DELETE 방식 요청
    fetch("/ajax/delete", {
        method :"DELETE",  // DELETE 방식 요청 0> @DeleteMapping 처리
        headers : {"Content-Type" :"application/json" },  //데이터를 하나를 전달해도 application/json 작성
        body : todoNo //todoNo값을 body에 담아서 전달
                        // ->@RequestBody로 꺼냄
    })
    
    .then(response => response.text())  //요청 결과를 text 형태로 변환
    .then (result => {
        if(result>0) { //삭제 성공
            alert("삭제 되었습니다.");

            //상세 조회 창 닫기
            popupLayer.classList.add("popup-hidden");
            
            //전체, 완료된 할 일 개수 다시 조회
            // + 할 일 목록 다시 조회
            getTotalCount();
            getCompleteCount();
            selectTodoList();

        }else{
            alert("삭제 실패..");
        }
    })

});



//-------------------------------------------------------------------------
//완료 여부 변경 버튼 눌렀을 때
completeBtn.addEventListener("click", ()=>{

    const todoNo= popupTodoNo.innerText;
    const complete = popupComplete.innerText === 'Y' ? 'N':'Y';

    //sql 수행에 필요한 값은 객체로 묶음
    const obj={
        "todoNo":todoNo,
        "complete" : complete

    };

    fetch("/ajax/changeComplete",{
        method:"PUT",
        headers : {"Content-Type" : "application/json"},
        body:JSON.stringify(obj)
    })
    .then(response=>response.text())
    .then(result =>{
        if(result>0){

            const count =  Number(completeCount.innerText)

            //update된 DB데이터를 다시 조회해서 화면에 출력
            popupComplete.innerText=complete;
            if(complete==='Y')   completeCount.innerText=count+1 ;
            else                 completeCount.innerText=count-1;
            selectTodoList();
            getCompleteCount();

        }else{
            alert('완료 여부 변경 실패');
        }

    })

})





//-------------------------------------------------------------------------

//상세 조회에서 수정 버튼 클릭 시 (#updateBtn)
updateView.addEventListener("click",()=>{


    //기존 팜업 레이어 숨기고
    popupLayer.classList.add("popup-hidden");

    //수정 레이어 보이게
    updateLayer.classList.remove("popup-hidden");

    //수정 레이어 보일 때
    // 팝업 레이어에 작성된 제목, 내용 얻어와 세팅
    updateTitle.value=popupTodoTitle.innerText;
    updateContent.value=popupTodoContent.innerHTML.replaceAll("<br>","\n");
    //=> HTML 화면에서 줄바꿈이 <br>로 인식되고 있는데
    //   textarea에서는 \n으로 바꿔야 줄 바꿈이 인식된다!


    //수정 레이어 -> 수정 버튼에 data-todo-no 속성 추가
    updateBtn.setAttribute("data-todo-no",popupTodoNo.innerText);

    updateBtn.addEventListener("click",e=>{
        const obj ={
            "todoNo" : e.target.dataset.todoNo,
            "todoTitle" : updateTitle.value,
            "todoContent" : updateContent.value
        }

        // console.log(obj);

        //비동기 요청
        fetch("/ajax/update",{
            method:"PUT",
            headers: {"Content-Type" : "application/json"},
            body : JSON.stringify(obj)
        })

        .then(response=>response.text())
        .then(result =>{

            if(result>0){
                alert("수정 성공")
                
                updateLayer.classList.add("popup-hidden"); //수정 레이어 숨기기

                //selectTodo();
                popupTodoTitle.innerText=updateTitle.value;
                popupTodoContent.innerHTML=updateContent.value.replaceAll("\n","<br>");
                selectTodoList();

                updateTitle.value="";  //남은 흔적 제거
                updateContent.value="";
                updateBtn.removeAttribute("data-todo-no"); //속성 제거

                popupLayer.classList.remove("popup-hidden");


            }else{
                alert("수정 실패");
            }
        })
    })

})




//-------------------------------------------------------------------------
//취소 버튼을 눌렀을 때 (#viewClose)
viewClose.addEventListener("click",()=>{
    updateLayer.classList.add("popup-hidden");
    popupLayer.classList.remove("popup-hidden");
})

//-------------------------------------------------------------------------







//js 파일에 함수 호출 코드 작성 -> 페이지 로딩 시 바로 실행
getTotalCount();
getCompleteCount();
selectTodoList();


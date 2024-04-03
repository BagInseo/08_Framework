/* 쿠키에서 key가 일치하는 value 얻어오기 함수 */
// 쿠키는 "K=V ; K:V" 형식

//배열.map(함수) : 배열의 각 요소를 이용해 함수 수행 후
//                  결과 값으로 새로운 배열을 만들어서 반환
const getCookie = key=>{
    const cookies = document.cookie; //"K=V ; K:V"
    
    //cookies 문자열을 배열 형태로 변환
    const cookieList = cookies.split("; ") //["K=V","K=V"]
                                .map(el=>{return el.split("=")}); //["k","v"]


    //배열 -> 객체로 변환(그래야 다루기 쉽다)
    const obj={}; // 비어있는 객체 생성
    for(let i=0; i<cookieList.length; i++){
        const k =cookieList[i][0];
        const v =cookieList[i][1];
        obj[k]=v;
    }

    return obj[key]; //매개변수로 전달 받은 key와 
                    // obj 객체에 저장된 키가 일치하는 요소의 값 반환

};

const loginForm = document.querySelector("#loginForm");
const loginEmail = document.querySelector("#loginForm input[name='memberEmail']");
const loginPw = document.querySelector("#loginForm input[name='memberPw']");

//로그인 안된 상태인 경우에만 수행
if(loginEmail!=null){ //로그인창의 이메일 입력 부분이 있을 때
    //쿠키 중 key값이 "saveId"인 요소의 value를 얻어오기
    const saveId = getCookie("saveId"); // undifined 또는 이메일

    //saveId 값이 있을 경우
    if(saveId!=undefined){
        loginEmail.value= saveId; //쿠키에서 얻어온 값을 input에 value로 세팅
        
        //아이디 저장 체크박스에 체크 해두기
        document.querySelector("input[name='saveId']").checked=true;
    }

};


/* 이메일, 비밀번호 미작성시 로그인 막기 */
//#loginForm이 화면에 존재할 떄 == 로그인 상태가 아닐 때
if(loginForm!=null){

   loginForm.addEventListener("submit",e=>{

    if(loginEmail.value.trim().length===0){
        alert("이메일을 입력하세요");
        e.preventDefault(); //기본 이벤트(제출) 막기
        loginEmail.focus();
        return;
    } 
    if(loginPw.value.trim().length===0) {
        alert("비밀번호를 입력하세요");
        e.preventDefault(); //기본 이벤트(제출) 막기
        loginPw.focus();
        return;
    }

   });

}


/* 빠른 로그인 */
const quickLoginBtns = document.querySelectorAll(".quick-login");


quickLoginBtns.forEach( (item, index) => {
  // item  : 현재 반복 시 꺼낸온 객체
  // index : 현재 반복 중인 인덱스

  // quickLoginBtns 요소를 하나씩 꺼내서 이벤트 리스너 추가
  item.addEventListener("click", e => {

    const email = item.innerText; // 버튼에 작성된 이메일 얻어오기

    location.href = "/member/quickLogin?memberEmail=" + email;
  });

});

// ---------------------------------------










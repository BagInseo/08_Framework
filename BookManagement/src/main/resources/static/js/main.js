const selectBtn = document.querySelector("#selectBtn"); //조회 버튼
const bookList= document.querySelector("#bookList"); //tbody

const createTd=(text)=>{
    const td = document.createElement("td");
    td.innerText=text;
    return td;
}

selectBtn.addEventListener("click",()=>{

    //비동기로 회원 목록 조회
    fetch("/book/selectBookList")
    .then(response=>response.json())
    .then(list =>{
        console.log(list);

        bookList.innerHTML="";

        list.forEach((book,index) => {
            const keyList=['bookNo','bookTitle','bookWriter','bookPrice','regDate'];
            const tr = document.createElement("tr");

            keyList.forEach(key => tr.append(createTd(book[key])));

        
            bookList.append(tr);

            
        });
    })
})

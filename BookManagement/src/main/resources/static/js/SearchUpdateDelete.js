const searchBtn = document.querySelector("#searchBtn"); //조회 버튼
const SearchBookList= document.querySelector("#SearchBookList"); //검색된 도서 리스트
const bookTitle = document.querySelector("#bookTitle");  //검색한 글자

const createTd=(text)=>{
    const td = document.createElement("td");
    td.innerText=text;
    return td;
}

searchBtn.addEventListener("click",()=>{

    fetch("/book/SearchUpdateDelete",{
        method:"POST",
        headers:{"Content-Type" : "application/json"},
        body:bookTitle.value
    })
    .then(response=>response.json())
    .then(searchList=>{
        console.log(searchList);

        SearchBookList.innerText = "";
        searchList.forEach((book, index) => {
            const keyList = ['bookNo', 'bookTitle', 'bookWriter', 'bookPrice', 'regDate'];
            const tr = document.createElement("tr");
        
            keyList.forEach(key => tr.append(createTd(book[key])));
        
            const td1 = document.createElement("td");
        
            const updateBtn = document.createElement("button"); // 수정 버튼
            updateBtn.textContent = "수정";
            updateBtn.addEventListener("click", e => {
                let inputPrice = prompt("수정할 가격 입력");
                
                const bookNo = book['bookNo']; 
                const bookPrice = Number(inputPrice); 
                const param = {
                    "bookNo": bookNo,
                    "bookPrice": bookPrice
                };
        
                if (inputPrice != null) {
                    fetch("/book/updatePrice", {
                        method: "PUT",
                        headers: { "Content-Type": "application/json" },
                        body: JSON.stringify(param)
                    })
                    .then(response => response.text())
                    .then(result => {
                        if (result > 0){
                            alert("가격이 수정되었습니다.");
                            e.target.closest("tr").children[3].innerText=inputPrice;
                        }


                    })
                } else {
                    alert("가격 수정을 취소하였습니다");
                }
            });
            td1.append(updateBtn);


            const td2= document.createElement("td");
            const deleteBtn = document.createElement("button"); //삭제 버튼
            deleteBtn.textContent = "삭제";
            deleteBtn.addEventListener("click", e => {
                console.log("Delete 버튼 클릭됨");
                if(!confirm("정말 삭제 하시겠습니까?")) return;

                const bookNo=book['bookNo'];

                fetch("/book/delete",{
                    method:"DELETE",
                    headers:{"Content-Type" :"application/json" },
                    body: bookNo
                })
                .then(response=>response.text())
                .then(result=>{
                    if(result>0) {
                        e.target.closest("tr").remove();
                        alert("삭제에 성공하였습니다.");
                    }
                    else        alert("삭제 실패")
                })
            });
            td2.append(deleteBtn);
            


            tr.append(td1);
            tr.append(td2);

            SearchBookList.append(tr);       
        });
    })
})
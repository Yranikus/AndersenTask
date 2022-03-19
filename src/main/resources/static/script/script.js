
const getURL = "http://localhost:8081/getactivelistofstudents",           //URL для первого get
    postURL = "http://localhost:8081/updateVisits",          //URL для POST
    pastURL = "http://localhost:8081/getpresentstudents";         //URL для контроллера с датой


const tableBody = document.querySelector('.bodyContainer'),
    dateInput = document.querySelector('.calendar');
var buttons,
    students = [];





//GET запрос с обработкой ошибки
const getTableContent = (getURL, table, body = null) => {
    let response;
    if(body == null) {
        response = fetch(getURL);
    }
    if(body != null){
        response = fetch(getURL);
    }

        response.then(response => {
            if (response.status == 200) {
                return response.json();
            } else {
                alert(`Ошибочка с запросом вышла, а код вот такой ${response.status}`);
                throw new Error(response.status);
            }
        })
        .then(res => {
            res.forEach(item => {
                counter++;
                table.insertAdjacentHTML("beforeend",
                    `<tr class="studentRow">
                        <td class="row">${item.id}</td>
                        <td>${item.name}</td>
                        <td class="minWidth">
                            <button class="BBB ${item.check === 1 ? 'greenClass' : 'redClass'}" id="${item.id}">
                            </button>
                        </td>
                    </tr>`);
            })
        }).then(() => {
        buttons = document.querySelectorAll('.BBB');
    });
};



//EventListener а весь документ, чтобы отлавливая нажатие по кнопкам изменять список присутствующих

document.addEventListener('click', (e) => {
    if(e.target.classList.contains("BBB")){

        if(e.target.classList.contains("redClass")){
            e.target.classList.remove('redClass');
            e.target.classList.add('greenClass');
            students.push(+e.target.id);
            console.log(students);
        }

        else if(e.target.classList.contains("greenClass")){
            e.target.classList.remove('greenClass');
            e.target.classList.add('redClass');
            students.splice(students.indexOf((e.target.id)), 1);
        }
    }
})


//Обработчик изменнения даты, чтобы просмотреть и добавить тех, кто не был отмечен раньше
//
//
//
//
//
dateInput.addEventListener('input', () => {
    tableBody.innerHTML = "";
    students.length = 0;
    console.log(dateInput.value);
                                 //  |
                                  // \/
    getTableContent(pastURL + `?date=${dateInput.value}`, tableBody);
})

//
//
//


//POST запрос с проверкой наличия значений даты
const sendBtn = document.querySelector('.sendBtn');

console.log(dateInput);


const postData = async (url, data) => {
    const result = await fetch(url, {
        method: 'POST',
        headers: {
            'Content-type': 'application/json'
        },
        body: data
    });

    return result;
};


sendBtn.addEventListener('click', async () => {

    if(dateInput.value === ''){
        const errorDiv = document.createElement('div');
        errorDiv.classList.add('errorField')
        errorDiv.innerText = "А поле даты то пустое";
        document.querySelector('.buttonWrapper').insertAdjacentElement('afterend', errorDiv);
        setTimeout(() => {
            errorDiv.remove();
        }, 800)
    }
    else{
        console.log(JSON.stringify(students));
        let kek = {
            date: dateInput.value,
            presentStudents: students
        }
        await postData(postURL, JSON.stringify(kek))
            .then(data => {
                console.log(data);
                alert("Данные ушли");
            })
        location.replace("teams");
    }

})


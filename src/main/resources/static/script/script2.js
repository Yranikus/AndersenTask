
const getURL = "http://localhost:8082/teams",           //URL для первого get
    postURL = "http://localhost:8082/updateMarks"          //URL для POST


const tableBody = document.querySelector('.bodyContainer'),
    dateInput = document.querySelector('.calendar');
let buttons,
    students = [];
let maxNumber = 0;
var numbers = [];
let counter = 1;

let thead = document.createElement('thead');


//GET запрос с обработкой ошибки
const getTableContent = (getURL) => {
    let counter = 0;
    let response;
        response = fetch(getURL);
        response.then(response => {
            if (response.status == 200) {
                return response.json();
            } else {
                alert(`Ошибочка с запросом вышла, а код вот такой ${response.status}`);
                throw new Error(response.status);
            }
        })
        .then(res => {
            students.splice(0,students.length)
            res.forEach(item => {
                let table = document.createElement('table'),
                commandNumber = document.createElement('div')
                tableBody.insertAdjacentElement('beforeend', table);

                commandNumber.innerText = `Команда № ${item.numberOfTeam}`;
                table.insertAdjacentElement('beforebegin', commandNumber);
                table.insertAdjacentHTML("beforeend",
                    `<tr>
                        <th scope="col" class="row">PK</th>
                        <th scope="col" class="row">№</th>
                        <th scope="col">ФИО ученика</th>
                        <th scope="col" class="minWidth">Первичные баллы</th>
                        <th scope="col" class="minWidth">Заработанные баллы</th>
                        <th scope="col" class="min">Ответ</th>
                        <th scope="col" class="min">Вопрос</th>
                    </tr>`);

                        var kek = item.students;
                        students = students.concat(kek);
                        console.log(students);
                        kek.forEach(seconditem =>{
                            maxNumber++;
                            counter++;
                        table.insertAdjacentHTML("beforeend",
                    `<tr class="studentRow">
                        <td class="row">${seconditem.id}</td>
                        <th scope="col" class="row">${counter}</th>
                        <td>${seconditem.name}</td>
                        <td>${seconditem.primaryScore}</th>
                        <td>${seconditem.score}</th>
                        <td class="minWidth">
                            <button class="BBB FFF ${seconditem.marksForLesson.answer === 1 ? 'greenClass' : 'redClass'}" id="${seconditem.id}">
                            </button>
                        </td>
                        <td class="minWidth">
                            <button class="BBB AAA ${seconditem.marksForLesson.question === 1 ? 'greenClass' : 'redClass'}" id="${seconditem.id}">
                            </button>
                        </td>
                    </tr>`);
                    });
            })
        }).then(() => {
        buttons = document.querySelectorAll('.BBB');
    });
};

// getTableContent(getURL);

function generator(Numbers) {
    let c = Math.floor(Math.random() * maxNumber) + 1;
    while (numbers.indexOf(c) != -1) {
        c = Math.floor(Math.random() * maxNumber) + 1;
    }
    numbers.push(c);
    return c;
}

const knopka = document.querySelector(".knopka");
const answerer = document.querySelector(".answerer");
const asker = document.querySelector(".asker")

let s = 0;

knopka.addEventListener('click', (e) => {
    if(dateInput.value === '') {
        const errorDiv = document.createElement('div');
        errorDiv.classList.add('errorField')
        errorDiv.innerText = "А поле даты то пустое";
        document.querySelector('.buttonWrapper').insertAdjacentElement('afterend', errorDiv);
        setTimeout(() => {
            errorDiv.remove();
        }, 800)
    }
    else {
        if (counter <= maxNumber) {
            if (s === 0) {
                asker.innerHTML = generator(maxNumber);
                answerer.innerHTML = generator(maxNumber);
                counter++;
                counter++;
                s = 1;
            } else {
                asker.innerHTML = answerer.innerHTML;
                answerer.innerHTML = generator(maxNumber);
                counter++;
            }
        } else {
            asker.innerHTML = answerer.innerHTML;
            answerer.innerHTML = numbers[0];
        }
    }
    console.log(numbers);
})



//EventListener а весь документ, чтобы отлавливая нажатие по кнопкам изменять список присутствующих

document.addEventListener('click', (e) => {
    if(e.target.classList.contains("FFF")){

        if(e.target.classList.contains("redClass")){
            e.target.classList.remove('redClass');
            e.target.classList.add('greenClass');
            changeAnswer(`${e.target.id}`, 1);
        }

        else if(e.target.classList.contains("greenClass")){
            e.target.classList.remove('greenClass');
            e.target.classList.add('redClass');
            changeAnswer(`${e.target.id}`, 0);
        }
    }
})



document.addEventListener('click', (e) => {
    if(e.target.classList.contains("AAA")){

        if(e.target.classList.contains("redClass")){
            e.target.classList.remove('redClass');
            e.target.classList.add('greenClass');
            changeQuestion(`${e.target.id}`, 1);
            console.log(students);
        }

        else if(e.target.classList.contains("greenClass")){
            e.target.classList.remove('greenClass');
            e.target.classList.add('redClass');
            changeQuestion(`${e.target.id}`, 1);
        }
    }
})

function changeQuestion( value, desc ) {
    for (var i in students) {
        if (students[i].id == value) {
            students[i].marksForLesson.question = desc;
            break; //Stop this loop, we found it!
        }
    }
}

function changeAnswer( value, desc ) {
    for (var i in students) {
        if (students[i].id == value) {
            students[i].marksForLesson.answer = desc;
            break; //Stop this loop, we found it!
        }
    }
}

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
    getTableContent(getURL + `?date=${dateInput.value}`);
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

    return await result.json()
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
            studentsWithMarks: students
        }
        await postData(postURL, JSON.stringify(kek))
            .then(data => {
                console.log(data);
                alert("Данные ушли");
                location.reload();
            })
    }

})


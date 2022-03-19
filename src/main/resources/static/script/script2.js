
const getURL = "http://localhost:8081/getteams",           //URL для первого get
    postURL = "http://localhost:8081/updateMarks"          //URL для POST


const tableBody = document.querySelector('.bodyContainer'),
    dateInput = document.querySelector('.calendar');
let buttons,
    students = [];
let maxNumber = 0;
let teamCounter = -1;
let arrayList = [];

let ansTeamNumber,
    k = 0,
    size = 0,
    second = 0,
    answerer,
    askTeamNumber = 0,
    asker,
    firstAsker;


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
                teamCounter++;
                arrayList.push([]);
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
                            arrayList[teamCounter].push(counter);
                        table.insertAdjacentHTML("beforeend",
                    `<tr class="studentRow">
                        <td class="row">${seconditem.id}</td>
                        <th scope="col" class="row">${counter}</th>
                        <td>${seconditem.name}</td>
                        <td>${seconditem.primaryScore}</th>
                        <td>${seconditem.score}</th>
                        <td class="minWidth">                        
                            <input type="text" class="ans id${seconditem.id}" style="width: 50%" value="${seconditem.marksForLesson.answer}" id="${seconditem.id}">  
                        </td>
                        <td class="minWidth">
                            <input type="text" class="que id${seconditem.id}" style="width: 50%" value="${seconditem.marksForLesson.question}" id="${seconditem.id}">                                         
                        </td>
                    </tr>`);
                    });
            })
        }).then(() => {
            askTeamNumber = random(arrayList.length);
            students.forEach(item => {
                size = size + item.length;
            })
            while (arrayList[askTeamNumber].length === 1 && size !== arrayList[askTeamNumber].length) {
                askTeamNumber = random(arrayList.length);
            }
            ansTeamNumber = random(arrayList.length);
            console.log(askTeamNumber)
            asker = random(arrayList[askTeamNumber].length);
            console.log(asker)
            console.log(arrayList)
            firstAsker = arrayList[askTeamNumber][asker];
        buttons = document.querySelectorAll('.BBB');
    });
};

// getTableContent(getURL);

const knopka = document.querySelector(".knopka");
const answererDiv = document.querySelector(".answerer");
const askerDiv = document.querySelector(".asker")

let s = 0;

const tr = (listListov) => {
    let t = 0;
    listListov.forEach(item => {
        if(item.length){
            t++
        }
    })
    return t
}

const random = (max) => {
    return Math.trunc (Math.random() * max);
}


function setMarks(){
    students.forEach(item => {
        item.marksForLesson.answer = document.querySelector(`.ans.id${item.id}`).value;
        item.marksForLesson.question = document.querySelector(`.que.id${item.id}`).value;
    })
}


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
        if(tr(arrayList) > 1) {
            console.log(arrayList[1].length)
            while (askTeamNumber === ansTeamNumber || arrayList[ansTeamNumber].length === 0) {
                ansTeamNumber = random(arrayList.length)
            }
            answerer = random(arrayList[ansTeamNumber].length)
            console.log('Спрашивает' + arrayList[askTeamNumber][asker])
            console.log('Отвечает' + arrayList[ansTeamNumber][answerer])
            askerDiv.innerHTML = arrayList[askTeamNumber][asker]
            answererDiv.innerHTML = arrayList[ansTeamNumber][answerer]
            if (arrayList[ansTeamNumber][answerer] === firstAsker && k > 0){
                arrayList[ansTeamNumber].splice(answerer, 1)
                answerer--;
                if (tr(arrayList) === 0 || tr(arrayList) === 1) {
                    return;
                }
                if (arrayList[ansTeamNumber].length === 0){
                    while (arrayList[ansTeamNumber].length === 0 || askTeamNumber === ansTeamNumber){
                        ansTeamNumber = random(arrayList.length);
                        console.log(1000-7);
                    }
                }
                if (answerer < 0) {
                    answerer = random(arrayList[ansTeamNumber].length);
                }
                answerer = random(arrayList[ansTeamNumber].length);
                second = arrayList[ansTeamNumber][answerer];
            }
            if (k !== 0) {
                arrayList[askTeamNumber].splice(asker, 1)
            }
            k++;
            asker = answerer
            askTeamNumber = ansTeamNumber
            return;
        }
        if(arrayList[askTeamNumber].length > 1) {
            while ((answerer === asker || answerer >= arrayList[askTeamNumber].length) && arrayList[askTeamNumber].length !== 1) {
                answerer = random(arrayList[askTeamNumber].length)
                console.log(answerer)
            }
            if (arrayList[askTeamNumber][asker] !== arrayList[askTeamNumber][answerer]) {
                console.log('Спрашивает' + arrayList[askTeamNumber][asker])
                console.log('Отвечает' + arrayList[askTeamNumber][answerer])
                askerDiv.innerHTML = arrayList[askTeamNumber][asker]
                answererDiv.innerHTML = arrayList[ansTeamNumber][answerer]
            }
            else {
                console.log('Спрашивает' + arrayList[askTeamNumber][asker])
                console.log('Отвечает' + second)
                askerDiv.innerHTML = arrayList[askTeamNumber][asker]
                answererDiv.innerHTML = second
            }
            arrayList[askTeamNumber].splice(asker, 1)
            if (answerer > 0) asker = answerer - 1;
            else asker = answerer;
            if (arrayList[askTeamNumber].length === 1){
                arrayList[askTeamNumber].splice(0, 1)
            }
        }
        if (arrayList[askTeamNumber].size() > 0) {
            askerDiv.innerHTML = arrayList[askTeamNumber][0];
            answererDiv.innerHTML = second;
        }
    }
})






dateInput.addEventListener('input', () => {
    tableBody.innerHTML = "";
    students.length = 0;
    console.log(dateInput.value);
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
            studentsWithMarks: students
        }
        setMarks();
        console.log(students);
        await postData(postURL, JSON.stringify(kek))
            .then(data => {
                console.log(data);
                alert("Данные ушли");
                location.reload();
            })
    }

})


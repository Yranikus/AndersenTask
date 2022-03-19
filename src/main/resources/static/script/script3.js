
const getURL = "http://localhost:8081/getteams",
    repoPost = "http://localhost:8081/setrepo"


const tableBody = document.querySelector('.bodyContainer'),
    dateInput = document.querySelector('.calendar');
let buttons,
    students = [];
let maxNumber = 0;
var numbers = [];
let counter = 1;

let thead = document.createElement('thead');

let commands = []
//GET запрос с обработкой ошибки
const getTableContent = (getURL) => {
    let counter = 0;
    let response;
        response = fetch(getURL);
        response.then(response => {
            if (response.status == 200) {
                console.log('ff')
                return response.json();
            } else {
                alert(`Ошибочка с запросом вышла, а код вот такой ${response.status}`);
                throw new Error(response.status);
            }
        })
        .then(res => {
            students.splice(0,students.length)
            res.forEach((item, key) => {
                let table = document.createElement('table'),
                    tableHeader = document.createElement('div'),
                    commandNumber = document.createElement('div'),
                    repoBtn = document.createElement('div'),
                    repoDiv;

                repoBtn.style.cssText = "width: 20px; height: 20px; background-color: teal; cursor: pointer;"
                repoBtn.classList.add('repoBtn');
                repoBtn.id = `repoBtn${key}`;


                commands.push(item.numberOfTeam);
                console.log(commands);

                if(item.repo != null){
                    repoDiv = document.createElement('div')
                    repoDiv.innerText = `Репозиторий: ${item.repo}`;
                }
                else{
                    repoDiv = document.createElement('input');
                    repoDiv.classList.add('repoDiv');
                    repoDiv.placeholder="Введите репозиторий"
                    tableHeader.insertAdjacentElement('beforeend', repoBtn);
                }

                repoDiv.id = `repo${key}`
                console.log(+repoDiv.id.replace('repo', ''))

                tableHeader.classList.add('tableHeader');
                tableHeader.insertAdjacentElement('afterbegin', repoDiv);
                tableHeader.insertAdjacentElement('afterbegin', commandNumber);




                tableBody.insertAdjacentElement('beforeend', table);

                commandNumber.innerText = `Команда № ${item.numberOfTeam}`;
                table.insertAdjacentElement('beforebegin', tableHeader);
                table.insertAdjacentHTML("beforeend",
                    `<tr>
                        <th scope="col" class="row">PK</th>
                        <th scope="col" class="row">№</th>
                        <th scope="col">ФИО ученика</th>
                        <th scope="col" class="minWidth">Первичные баллы</th>
                        <th scope="col" class="minWidth">Заработанные баллы</th>
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
                        
                    </tr>`);
                    });
            })
        }).then(() => {
        buttons = document.querySelectorAll('.BBB');
    });
};




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

    if(e.target.classList.contains("repoBtn")){
        let index = +e.target.id.replace('repoBtn', '')
        console.log(index);
        let repo = document.querySelector(`#repo${index}`).value
        console.log(document.querySelector(`#repo${index}`).value)

        let obj = {
            command: commands[index],
            repo: repo
        }

        fetch(repoPost + `?id=${obj.command}&repo=${obj.repo}`, {
            method: 'POST',
            headers: {
                'Content-type': 'application/json'
            },
            body: obj
        });
        alert("Репозиторий обновился");
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



dateInput.addEventListener('input', () => {
    tableBody.innerHTML = "";
    students.length = 0;
    console.log(dateInput.value);
                                 //  |
                                  // \/
    getTableContent(getURL + `?date=${dateInput.value}`);
})





const sendBtn = document.querySelector('.sendBtn');

console.log(dateInput);


const postData = async (url, data) => {
    const result = await fetch(url +`?id=${data.command}&repo=${data.repo}`, {
        method: 'POST',
        headers: {
            'Content-type': 'application/json'
        },
    });

    return result;
};






// sendBtn.addEventListener('click', async () => {
//
//     if(dateInput.value === ''){
//         const errorDiv = document.createElement('div');
//         errorDiv.classList.add('errorField')
//         errorDiv.innerText = "А поле даты то пустое";
//         document.querySelector('.buttonWrapper').insertAdjacentElement('afterend', errorDiv);
//         setTimeout(() => {
//             errorDiv.remove();
//         }, 800)
//     }
//     else{
//         console.log(JSON.stringify(students));
//         let kek = {
//             date: dateInput.value,
//             studentsWithMarks: students
//         }
//         await postData(postURL, JSON.stringify(kek))
//             .then(data => {
//                 console.log(data);
//                 alert("Данные ушли");
//                 location.reload();
//             })
//     }
//
// })


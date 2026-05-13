function functions(){
    let news1 = document.getElementById("news1");
    let news2 = document.getElementById("news2");
    news1.innerHTML = welcome()+"<br/>"+date()+"<br/>";
    news2.innerHTML = daysToBirthday();
}

function welcome(){
    let today = new Date();
    let hour = today.getHours();
    if( (hour<18) && (hour>=6) ){
        return 'Good morning, ';
    }else{
        return 'Good evening, ';
    }
}

function date(){
    let months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
    let today = new Date();
    let day = today.getDate();
    let month = months[today.getMonth()];
    let year = today.getFullYear();

    return 'today is '+month+' '+day+' '+' '+year+'.';
}

function daysToBirthday(){
    // Day / month of the birthday (change these values as needed)
    let birthdayDay = 21;
    let birthdayMonth = 3;

    let today = new Date();
    let year = today.getFullYear();

    // Date object months are 0-based (0 = January)
    let birthday = new Date(year, birthdayMonth - 1, birthdayDay);

    if (today > birthday) {
        birthday.setFullYear(year + 1);
    }

    let diff = birthday - today;
    let days = Math.ceil(diff / (1000 * 60 * 60 * 24));

    return 'The Autor of this page will have a birthday in ' + days + ' days.';
}

function clock() {
    const clockEl = document.getElementById('clock');
    if (!clockEl) return;

    const now = new Date();
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    const seconds = String(now.getSeconds()).padStart(2, '0');

    clockEl.textContent = `${hours}:${minutes}:${seconds}`;
}
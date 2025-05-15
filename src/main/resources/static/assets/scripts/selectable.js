const yourDate = new Date()
const $fcHeaderToolbar = document.querySelector('#calendar .fc-header-toolbar');
let calendar;

document.addEventListener('DOMContentLoaded', function() {
    const calendarEl = document.getElementById('calendar');
    calendar = new FullCalendar.Calendar(calendarEl, {
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek'
        },
        initialDate: `${yourDate.toISOString().split('T')[0]}`,
        navLinks: true, // can click day/week names to navigate views
        navLinkDayClick: function(date, jsEvent) {
            let calendarApi = calendar.view.calendar;
            calendarApi.gotoDate(date);
            calendarApi.changeView('timeGridWeek'); // changeview(day) -> changeview(week)
        },
        selectable: true,
        selectMirror: true,
/*        select: function(arg) {
            const title = prompt('Event Title:');
            if (title) {
                calendar.addEvent({
                    title: title,
                    start: arg.start,
                    end: arg.end,
                    allDay: arg.allDay,
                })
            }
            calendar.unselect()
        }*/
        eventClick: function(arg) {
            if (confirm('Are you sure you want to delete this event?')) {
                arg.event.remove()
            }
        },
        editable: true,
        dayMaxEvents: true, // allow "more" link when too many events
        events: function(fetchInfo, successCallback, failureCallback) {
            const xhr = new XMLHttpRequest();
            xhr.onreadystatechange = () => {
                if (xhr.readyState !== XMLHttpRequest.DONE) {
                    return;
                }
                if (xhr.status < 200 || xhr.status >= 300) {
                    alert(xhr.status);
                    return;
                }
                const res = JSON.parse(xhr.responseText);
                console.log(res);
                if (Array.isArray(res)) {
                    successCallback(res);
                } else {
                    alert('type error!!!');
                    failureCallback('response data type error');
                }
            };
            xhr.open('GET', '/request/list');
            xhr.send();
        },
    });



    calendar.render();
});



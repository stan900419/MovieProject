function loadSessions() {

    $.ajax({
        url: "http://localhost:8080/movieproject_back-end-new/selectroom",
        method: "GET",
        dataType: "json",
        success: function (data) {
            const tableBody = $('#sessionTable tbody');
            tableBody.empty();

            data.forEach(session => {
                const row = $('<tr></tr>');
                const formattedDate = formatDate(session.playdate);
                const formattedTime = formatTime(session.playtime);

                row.append($('<td></td>').text(session.roomname));
                row.append($('<td></td>').text(session.roomusing));
                row.append($('<td></td>').text(session.movieid));
                row.append($('<td></td>').text(session.roommovie));
                row.append($('<td></td>').text(formattedDate));
                row.append($('<td></td>').text(formattedTime));
                row.append($('<td></td>').text(session.seats));
                row.append($('<td></td>').text(session.session));

                const editButton = $('<button></button>').text('修改').click(() => openEditModal(session));
                const deleteButton = $('<button></button>').text('刪除').click(() => {
                    deleteSession(session.id, session.session);
                });
                const seatButton = $('<button></button>')
                    .text('座位狀態')
                    .click(() => {
                        loadSeatStatus(session.session);
                        // window.location.href = "#seatInfoContainer";
                        $('html, body').animate({
                            scrollTop: $('#seatInfoContainer').offset().top
                        }, 500); // 500 為動畫持續時間（毫秒）
                    });

                const actionsCell = $('<td></td>').append(editButton, deleteButton, seatButton);
                row.append(actionsCell);

                tableBody.append(row);
            });
        },
        error: function (err) {
            console.log("Error loading sessions:", err);
        }
    });
}

function loadMovies() {
    $.ajax({
        url: "http://localhost:8080/movieproject_back-end-new/movieget",
        method: "POST",
        dataType: "json",
        success: function (data) {
            const movieSelect = $('#editMovieSelect');
            movieSelect.empty();

            data.forEach(movie => {
                movieSelect.append($('<option></option>').val(movie.movieid).text(movie.movieid + " - " + movie.movieName));
            });
        },
        error: function (err) {
            console.log("Error loading movies:", err);
        }
    });
}

function formatDate(rawDate) {
    const [monthStr, day, year] = rawDate.split(/[\s,]+/);
    const monthMap = {
        '1月': '01', '2月': '02', '3月': '03', '4月': '04', '5月': '05',
        '6月': '06', '7月': '07', '8月': '08', '9月': '09', '10月': '10',
        '11月': '11', '12月': '12',
        'Jan': '1', 'Feb': '2', 'Mar': '3', 'Apr': '4', 'May': '5', 'Jun': '6',
        'Jul': '7', 'Aug': '8', 'Sep': '9', 'Oct': '10', 'Nov': '11', 'Dec': '12'
    };
    return `${year}/${monthMap[monthStr]}/${day.padStart(2, '0')}`;
}

function formatTime(rawTime) {
    const [time, period] = rawTime.split(' ');
    let [hour, minute] = time.split(':');
    if (period === '下午' && hour !== '12') hour = (parseInt(hour) + 12).toString();
    if (period === '上午' && hour === '12') hour = '00';
    return `${hour.padStart(2, '0')}:${minute}`;
}

function openEditModal(session) {
    // 隱藏座位狀態容器
    $('#seatInfoContainer').hide();
    $('#editSession').val(session.session);
    $('#editRoomId').val(session.id); // 设置房间 ID
    $('#editRoomName').val(session.roomname);
    $('#editRoomUsing').val(session.roomusing);
    $('#editMovieSelect').val(session.movieid); // 預設選擇當前的電影ID
    $('#editMovieName').val(session.roommovie); // 更新隱藏字段的電影名稱
    $('#editSeats').text(session.seats);
    $('#editSessionIdDisplay').text(session.session); // 顯示場次編號
    $('#editPlayDateDisplay').text(formatDate(session.playdate)); // 顯示放映日期
    $('#editPlayTimeDisplay').text(formatTime(session.playtime)); // 顯示放映時間
    $('#editModal').show();
}

$('#editMovieSelect').change(function () {
    const selectedMovieId = $(this).val();
    const selectedOption = $(this).find("option:selected").text(); // 获取选中的选项
    const selectedMovieName = selectedOption.split(" - ")[1]; // 获取电影名称
    $('#editMovieName').val(selectedMovieName); // 更新隐藏字段的电影名称
});

$('#editForm').submit(function (event) {
    event.preventDefault();
    const formData = $(this).serialize();
    $.ajax({
        url: "http://localhost:8080/movieproject_back-end-new/updateRoom",
        method: "POST",
        data: formData,
        success: function () {
            alert("更新成功！");
            $('#editModal').hide();
            loadSessions(); // 重新加載場次
        },
        error: function () {
            alert("更新失敗！");
        }
    });
});

function deleteSession(id, session) {
    if (confirm("確定要刪除這個房間與座位嗎？")) {
        $.ajax({
            url: "http://localhost:8080/movieproject_back-end-new/deleteRoomAndSeat",
            method: "POST",
            data: { id: id, session: session }, // 发送座位 id 和 session
            success: function (response) {
                alert("刪除成功！");
                loadSessions(); // 重新加載場次
            },
            error: function () {
                alert("刪除失敗！");
            }
        });
    }
}

function loadSeatStatus(sessionId) {
    // 隱藏編輯模態框
    $('#editModal').hide();
    // 顯示場次編號
    $('#sessionId').text(sessionId);

    $.ajax({
        url: "http://localhost:8080/movieproject_back-end-new/selectSeat",
        method: "POST",
        data: { session: sessionId },
        dataType: "json",
        success: function (data) {
            const seatContainer = $('.seat-container');
            seatContainer.empty(); // 清空座位容器

            data.forEach(seat => {
                const seatButton = $('<button></button>')
                    .addClass('seat-button')
                    .addClass(seat.seatstate === 'sell' ? 'sold' : 'available')
                    .text(seat.seatid)
                    .click(() => alert(`座位編號: ${seat.seatid}\n狀態: ${seat.seatstate || '未售出'}\n購買人: ${seat.memberno || '無'}`));
                seatContainer.append(seatButton);
            });

            // 顯示座位資訊容器
            $('#seatInfoContainer').show();
        },
        error: function (err) {
            console.log("Error loading seat status:", err);
        }
    });
}


$(document).ready(function () {
    loadSessions();
    loadMovies();
});

var movies = {};
var allSessions = [];

function generateSessionID() {
    const roomName = document.getElementById('roomname').value;
    const playDate = document.getElementById('playdate').value.replace(/-/g, '');
    const playTime = document.getElementById('playtime').value.slice(0, 5).replace(/:/g, '');

    if (roomName && playDate && playTime) {
        const sessionID = roomName + playDate + playTime;
        document.getElementById('sessionID').value = sessionID;
    }
}

function parseCustomDate(dateString) {
    const date = new Date(dateString);

    // 檢查日期是否有效
    if (isNaN(date.getTime())) {
        console.error("無效的日期格式:", dateString);
        return null; // 返回 null 表示解析失敗
    }
    return date;
}

function formatDate(dateString) {
    const date = parseCustomDate(dateString);

    // 檢查解析是否成功
    if (date === null) {
        return "無法格式化日期"; // 或者根據需要處理
    }

    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0'); // 月份從 0 開始
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}/${month}/${day}`;
}

function loadMovieIDs() {
    $.ajax({
        url: "http://localhost:8080/movieproject_back-end-new/movieget",
        method: "POST",
        dataType: "json",
        success: function (data) {
            movies = data.reduce((acc, movie) => {
                acc[movie.movieid] = movie;
                return acc;
            }, {});
            updateMovieTable(data);
            filterMoviesByPlaydate($('#playdate').val()); // 加載完電影後即時過濾
        },
        error: function (err) {
            console.log("Error loading movie IDs: " + err);
        }
    });
}

function updateMovieTable(data) {
    var movieTableBody = $('#movieTable tbody');
    movieTableBody.empty();

    data.forEach(function (movie) {
        var row = $('<tr></tr>');
        row.append($('<td></td>').text(movie.movieid));
        row.append($('<td></td>').text(movie.movieName));
        row.append($('<td></td>').text(formatDate(movie.moviestartdate)));
        row.append($('<td></td>').text(formatDate(movie.movieenddate)));
        row.append($('<td></td>').text(movie.runtime));
        movieTableBody.append(row);
    });
}

//抓電影清單中符合上/下映日期的電影
function filterMoviesByPlaydate(playdate) {
    if (!playdate) return;

    const selectedDate = new Date(playdate);

    const filteredMovies = Object.values(movies).filter(movie => {
        const startDate = parseDate(movie.moviestartdate);
        const endDate = parseDate(movie.movieenddate);

        return selectedDate >= startDate && selectedDate <= endDate;
    });

    updateMovieIDOptions(filteredMovies);
}




function updateMovieIDOptions(filteredMovies) {
    var movieSelect = $('#movieid');
    movieSelect.empty();

    if (filteredMovies.length === 0) {
        movieSelect.append('<option value="">無符合的電影</option>');
    } else {
        filteredMovies.forEach(function (movie) {
            var option = $('<option></option>')
                .attr("value", movie.movieid)
                .text(movie.movieid + " - " + movie.movieName);
            movieSelect.append(option);
        });
    }

    movieSelect.change(); // 觸發 change 事件以更新顯示
}


function displayMovieName() {
    var selectedMovieId = $('#movieid').val();
    var selectedMovieName = movies[selectedMovieId]?.movieName;
    if (selectedMovieName) {
        $('#moviename').val(selectedMovieName);
    }
}

//A廳40位,B:30,C:20
function handleRoomChange() {
    const roomName = document.getElementById('roomname').value;
    const seatNumberSelect = document.getElementById('seatnumber');

    let options = '';
    if (roomName === 'A') options = '<option value="40">40</option>';
    else if (roomName === 'B') options = '<option value="30">30</option>';
    else if (roomName === 'C') options = '<option value="20">20</option>';

    seatNumberSelect.innerHTML = options;
}


$('#v-pills-add-room-tab').on('click', loadSessions());
function loadSessions() {
    $.ajax({
        url: "http://localhost:8080/movieproject_back-end-new/selectroom",
        method: "GET",
        dataType: "json",
        success: function (data) {
            allSessions = data; // 解析JSON格式的數據並儲存所有場次數據
            alert("檢查點1:", JSON.stringify(allSessions));
            filterAndLoadSessions(); // 加載初始場次
        },
        error: function (err) {
            console.log("Error loading sessions: " + err);
        }
    });
}

function parseChineseDate(dateString) {
    const date = parseDate(dateString);
    if (!date) {
        console.error("日期解析失敗:", dateString);
        return null;
    }
    return date;
}



function filterAndLoadSessions() {
    const allSessions = window.sessionStorage.getItem("saveAllSessions");
    alert("檢查點2:" + allSessions);
    // loadSessions();
    console.log("filterAndLoadSessions called");
    const selectedDate = new Date($('#playdate').val());
    alert("Selected Date:", selectedDate);

    const filteredSessions = allSessions.filter(session => {
        const sessionDate = parseChineseDate(session.playdate);
        alert("Session Date:", sessionDate);
        if (!sessionDate) return false;

        return (
            sessionDate.getFullYear() === selectedDate.getFullYear() &&
            sessionDate.getMonth() === selectedDate.getMonth() &&
            sessionDate.getDate() === selectedDate.getDate()
        );
    });
    alert("檢查點3:" + filteredSessions);

    const tableBody = $('#matchSessionTable tbody');
    console.log("Filtered Sessions:", filteredSessions);
    tableBody.empty();

    if (filteredSessions.length === 0) {
        tableBody.append('<tr><td colspan="7">當前時段空閒</td></tr>');
    } else {
        filteredSessions.forEach(session => {
            const row = $('<tr></tr>');
            row.append($('<td></td>').text(session.roomname));
            row.append($('<td></td>').text(session.movieid));
            row.append($('<td></td>').text(session.roommovie));
            row.append($('<td></td>').text(parseChineseDate(session.playdate).toLocaleDateString()));
            row.append($('<td></td>').text(session.playtime));

            const endTime = calculateEndTime(session);
            row.append($('<td></td>').text(endTime));
            row.append($('<td></td>').text(session.session));

            tableBody.append(row);
        });
    }
}

function calculateEndTime(session) {
    const [hours, minutes] = session.playtime.split(':').map(Number);
    const startDate = new Date(0, 0, 0, hours, minutes); // 確保秒數設為0
    const runtime = movies[session.movieid]?.runtime || 0; // 獲取正確的電影時長
    const endDate = new Date(startDate.getTime() + runtime * 60000);
    return formatEndTime(endDate);
}

function formatEndTime(date) {
    const hours = date.getHours();
    const minutes = date.getMinutes();
    const period = hours >= 12 ? '下午' : '上午';
    const formattedHours = hours % 12 === 0 ? 12 : hours % 12; // 12小時制處理

    return `${String(formattedHours).padStart(2, '0')}:${String(minutes).padStart(2, '0')}:00 ${period}`;
}





function parseDate(dateString) {
    const months = {
        Jan: 0, Feb: 1, Mar: 2, Apr: 3, May: 4, Jun: 5,
        Jul: 6, Aug: 7, Sep: 8, Oct: 9, Nov: 10, Dec: 11,
        "1月": 0, "2月": 1, "3月": 2, "4月": 3, "5月": 4,
        "6月": 5, "7月": 6, "8月": 7, "9月": 8, "10月": 9,
        "11月": 10, "12月": 11
    };

    const parts = dateString.match(/(\w+)\s+(\d{1,2}),\s+(\d{4})(?:,\s+(\d{1,2}):(\d{2}):(\d{2})\s+([AP]M))?/);

    if (!parts) {
        console.error("無法解析日期:", dateString);
        return null;
    }

    const month = months[parts[1]];
    const day = parseInt(parts[2], 10);
    const year = parseInt(parts[3], 10);
    let hours = parts[4] ? parseInt(parts[4], 10) : 0;
    const minutes = parts[5] ? parseInt(parts[5], 10) : 0;
    const seconds = parts[6] ? parseInt(parts[6], 10) : 0;
    const period = parts[7];

    // 處理 AM/PM 時制
    if (period === 'PM' && hours < 12) hours += 12;
    if (period === 'AM' && hours === 12) hours = 0;

    return new Date(year, month, day, hours, minutes, seconds);
}





function submitForm() {
    const playTime = document.getElementById('playtime').value;
    const selectedMovieId = document.getElementById('movieid').value;
    const movieDuration = movies[selectedMovieId]?.runtime || 0;

    // 確保playTime格式為"HH:MM"
    const playTimeParts = playTime.split(':');
    const startDate = new Date();
    startDate.setHours(playTimeParts[0]);
    startDate.setMinutes(playTimeParts[1]);

    const endDate = new Date(startDate.getTime() + movieDuration * 60000);
    document.querySelector('form').submit(); // 提交表單
}


function checkForConflict() {
    const playDate = $('#playdate').val();
    const playTime = $('#playtime').val();
    const roomName = $('#roomname').val();

    const isConflict = allSessions.some(session =>
        session.playdate === playDate &&
        session.playtime === playTime &&
        session.roomname === roomName
    );

    if (isConflict) {
        $('#errorMessage').show();
        $('#submitButton').attr('disabled', true); // 禁用提交按鈕
    } else {
        $('#errorMessage').hide();
        $('#submitButton').attr('disabled', false); // 啟用提交按鈕
    }
}


// 在日期、時間或房間變動時檢查衝突
$('#playdate, #playtime, #roomname').on('change', checkForConflict);

$('form').on('submit', function (event) {
    event.preventDefault(); // 阻止表單的預設提交行為

    $.ajax({
        url: $(this).attr('action'),
        method: $(this).attr('method'),
        data: $(this).serialize(),
        success: function () {
            alert("場次新增成功！");
            location.reload(); // 成功後重新載入頁面
        },
        error: function (jqXHR) {
            if (jqXHR.status === 409) {
                $('#errorMessage').text("此時間已有重複場次，請重新選擇。").show();
            } else {
                console.log("提交失敗: " + jqXHR.statusText);
            }
        }
    });
});


function start() {
    $('#playdate').on('change', function () {
        const playdate = $(this).val();
        if (playdate) {
            filterMoviesByPlaydate(playdate);
            filterAndLoadSessions(); // 每次選擇放映日期時篩選場次
        }
    });
    handleRoomChange();
}

$(document).ready(function () {
    start();
    loadMovieIDs();
    // loadSessions();

});
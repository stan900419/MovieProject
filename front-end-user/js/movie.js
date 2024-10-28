function loadSessions() {
    $.ajax({
        url: "http://localhost:8080/movieproject_back-end/api/movie", // 更新為你的後端 API 路徑
        method: "GET",
        dataType: "json",
        success: function (data) {
            var tableBody = $('#sessionTable tbody');
            tableBody.empty(); // 清空表格

            data.forEach(function (session) {
                var row = $('<tr></tr>');

                // 處理 playdate 和 playtime 的格式化顯示
                var playdate = new Date(session.playdate);
                var formattedDate = playdate.toLocaleDateString(); // 顯示日期
                var playtime = new Date('1970-01-01T' + session.playtime); // 假設 playtime 是 "HH:mm:ss" 格式的字符串
                var formattedTime = playtime.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }); // 顯示時間
                row.append($('<td></td>').text(session.roomname)); // 確保 Room 對象有這個屬性
                row.append($('<td></td>').text(session.roommovie)); // 確保從後端獲得電影名稱
                row.append($('<td></td>').text(formattedDate)); // 確保 Room 對象有這個屬性
                row.append($('<td></td>').text(formattedTime)); // 確保 Room 對象有這個屬性
                row.append($('<td></td>').text(session.session)); // 確保 Room 對象有這個屬性
                tableBody.append(row);
            });
        },
        error: function (err) {
            console.log("Error loading sessions: " + err);
        }
    });
}

$(document).ready(loadSessions);
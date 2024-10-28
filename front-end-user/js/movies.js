
var movies = [];


function getAllMovie() {
    $.ajax({
        url: "http://localhost:8080/movieproject_back-end/api/movie/getAllMovies",
        method: "get",
        dataType: 'html',
        data: "",
        // success: function (res) { console.log("77_" + res) },
        error: function (err) { console.log("err_" + err) },
        success: function (data) {
            // console.log("movie data:" + data);
            movies = JSON.parse(data);
            $.each(movies, function (index) {
                if (index == 0) {
                    console.log("append__movie movieName");
                    var movieName = $("<h5></h5>");
                    movieName.text(movies[index].movieName);
                    $('#movie1_titlee').append(movieName);
                    var movieStarteDate = $("<h5></h5>");
                    movieStarteDate.text("上映日期:" + movies[index].moviestartdate);
                    $('#movie1_brieff').append(movieStarteDate);
                    movie1Div = document.getElementById('movie11');
                    movie1Div.addEventListener('click', function eventListener() {
                        console.log('Div' + index + ' was clicked!');
                        movie1Div.removeEventListener('click', eventListener);
                        $.ajax({
                            method: "post",
                            dataType: 'html',
                            url: "http://localhost:8080/movieproject_back-end/movieidsavemovie",
                            data: {
                                
                                'customername': window.localStorage.getItem("customername"),
                        'movieId': movies[index].movieid,
                                'movieName': movies[index].movieName,
                                'movieType': movies[index].movieType,
                                'movieStartDate': movies[index].moviestartdate,
                                'runtime': movies[index].runtime,
                                'moviePrice': 300
                            },
                            success: function (data) {
                                window.localStorage.removeItem("movieid");
                                window.localStorage.setItem("movieid", movies[index].movieid);
                                window.localStorage.removeItem("userName");
                                window.localStorage.setItem("userName", data);
                                console.log('stored movieid success:' + data);
                                var url = "./movieRoom.html";
                                location.href = url;
                            }
                        });
                    });
                }
                if (index == 1) {
                    console.log("append__movie movieName");
                    var movieName = $("<h5></h5>");
                    movieName.text(movies[index].movieName);
                    $('#movie2_titlee').append(movieName);
                    var movieStarteDate = $("<h5></h5>");
                    movieStarteDate.text("上映日期:" + movies[index].moviestartdate);
                    $('#movie2_briefff').append(movieStarteDate);
                    var movie2Divv = document.getElementById('movie22');
                    movie2Divv.addEventListener('click', function () {
                        console.log('Div' + index + ' was clicked!');
                        $.ajax({
                            method: "post",
                            dataType: 'html',
                            url: "http://localhost:8080/movieproject_back-end/movieidsavemovie",
                            data: {
                                'customername': window.localStorage.getItem("customername"),
                                        'movieId': movies[index].movieid,
                                'movieName': movies[index].movieName,
                                'movieType': movies[index].movieType,
                                'movieStartDate': movies[index].moviestartdate,
                                'runtime': movies[index].runtime,
                                'moviePrice': 300
                            },
                            success: function (data) {
                                window.localStorage.setItem("movieid", movies[index].movieid);
                                window.localStorage.setItem("userName", data);
                                console.log('stored movieid success:' + data);
                                var url = "./movieRoom.html";
                                location.href = url;
                            }
                        });
                    });
                }
                if (index == 2) {
                    console.log("append__movie movieName");
                    var movieName = $("<h5></h5>");
                    movieName.text(movies[index].movieName);
                    $('#movie3_titlee').append(movieName);
                    var movieStarteDate = $("<h5></h5>");
                    movieStarteDate.text("上映日期:" + movies[index].moviestartdate);
                    $('#movie3_brieff').append(movieStarteDate);
                    var movie3Divv = document.getElementById('movie33');
                    movie3Divv.addEventListener('click', function () {
                        console.log('Div' + index + ' was clicked!');
                        $.ajax({
                            method: "post",
                            dataType: 'html',
                            url: "http://localhost:8080/movieproject_back-end/movieidsavemovie",
                            data: {
                                'customername': window.localStorage.getItem("customername"),
                                        'movieId': movies[index].movieid,
                                'movieName': movies[index].movieName,
                                'movieType': movies[index].movieType,
                                'movieStartDate': movies[index].moviestartdate,
                                'runtime': movies[index].runtime,
                                'moviePrice': 300
                            },
                            success: function (data) {
                                window.localStorage.setItem("movieid", movies[index].movieid);
                                window.localStorage.setItem("userName", data);
                                console.log('stored movieid success:' + data);
                                var url = "./movieRoom.html";
                                location.href = url;
                            }
                        });
                    });
                }
                if (index == 3) {
                    console.log("append__movie movieName");
                    var movieName = $("<h5></h5>");
                    movieName.text(movies[index].movieName);
                    $('#movie4_titlee').append(movieName);
                    var movieStarteDate = $("<h5></h5>");
                    movieStarteDate.text("上映日期:" + movies[index].moviestartdate);
                    $('#movie4_brieff').append(movieStarteDate);
                    var movie4Divv = document.getElementById('movie44');
                    movie4Divv.addEventListener('click', function () {
                        console.log('Div' + index + ' was clicked!');
                        $.ajax({
                            method: "post",
                            dataType: 'html',
                            url: "http://localhost:8080/movieproject_back-end/movieidsavemovie",
                            data: {
                                'customername': window.localStorage.getItem("customername"),
                                        'movieId': movies[index].movieid,
                                'movieName': movies[index].movieName,
                                'movieType': movies[index].movieType,
                                'movieStartDate': movies[index].moviestartdate,
                                'runtime': movies[index].runtime,
                                'moviePrice': 300
                            },
                            success: function (data) {
                                window.localStorage.setItem("movieid", movies[index].movieid);
                                window.localStorage.setItem("userName", data);
                                console.log('stored movieid success:' + data);
                                var url = "./movieRoom.html";
                                location.href = url;
                            }
                        });
                    });
                }
                if (index == 4) {
                    console.log("append__movie movieName");
                    var movieName = $("<h5></h5>");
                    movieName.text(movies[index].movieName);
                    $('#movie5_titlee').append(movieName);
                    var movieStarteDate = $("<h5></h5>");
                    movieStarteDate.text("上映日期:" + movies[index].moviestartdate);
                    $('#movie5_brieff').append(movieStarteDate);
                    var movie5Divv = document.getElementById('movie55');
                    movie5Divv.addEventListener('click', function () {
                        console.log('Div' + index + ' was clicked!');
                        $.ajax({
                            method: "post",
                            dataType: 'html',
                            url: "http://localhost:8080/movieproject_back-end/movieidsavemovie",
                            data: {
                                'customername': window.localStorage.getItem("customername"),
                                        'movieId': movies[index].movieid,
                                'movieName': movies[index].movieName,
                                'movieType': movies[index].movieType,
                                'movieStartDate': movies[index].moviestartdate,
                                'runtime': movies[index].runtime,
                                'moviePrice': 300
                            },
                            success: function (data) {
                                window.localStorage.setItem("movieid", movies[index].movieid);
                                window.localStorage.setItem("userName", data);
                                console.log('stored movieid success:' + data);
                                var url = "./movieRoom.html";
                                location.href = url;
                            }
                        });
                    });
                }
                if (index == 5) {
                    console.log("append__movie movieName");
                    var movieName = $("<h5></h5>");
                    movieName.text(movies[index].movieName);
                    $('#movie6_titlee').append(movieName);
                    var movieStarteDate = $("<h5></h5>");
                    movieStarteDate.text("上映日期:" + movies[index].moviestartdate);
                    $('#movie6_brieff').append(movieStarteDate);
                    var movie6Divv = document.getElementById('movie66');
                    movie6Divv.addEventListener('click', function () {
                        console.log('Div' + index + ' was clicked!');
                        $.ajax({
                            method: "post",
                            dataType: 'html',
                            url: "http://localhost:8080/movieproject_back-end/movieidsavemovie",
                            data: {
                                'customername': window.localStorage.getItem("customername"),
                                        'movieId': movies[index].movieid,
                                'movieName': movies[index].movieName,
                                'movieType': movies[index].movieType,
                                'movieStartDate': movies[index].moviestartdate,
                                'runtime': movies[index].runtime,
                                'moviePrice': 300
                            },
                            success: function (data) {
                                window.localStorage.setItem("movieid", movies[index].movieid);
                                window.localStorage.setItem("userName", data);
                                console.log('stored movieid success:' + data);
                                var url = "./movieRoom.html";
                                location.href = url;
                            }
                        });
                    });
                }
            });
        }
    });
}
function start() {
    getAllMovie();
}
$(document).ready(start);
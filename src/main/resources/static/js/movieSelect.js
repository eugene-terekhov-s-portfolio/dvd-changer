function formatMovie (movie) {
      if (movie.loading) return movie.text;

      var markup = "<div class='select2-result-movie clearfix'>" +
        "<div class='select2-result-movie_poster'>" + 
        "<img src='https://image.tmdb.org/t/p/w92" + movie.poster_path + "' />" + 
        "</div>" +
        "<div class='select2-result-movie_meta'>" +
        "<div class='select2-result-movie_title'>" + movie.title +
        " (" + movie.original_title + 
        (movie.release_date.length > 0 ? ", " + movie.release_date.substring(0,4) : "") + ")</div>";

      if (movie.overview) {
        markup += "<div class='select2-result-movie_overview'>" + movie.overview + "</div>";
      }

      if (movie.vote_average) {
        markup += "<div>" +
          "<div class='select2-result-movie_rating'><span class='glyphicon glyphicon-star'></span> " + movie.vote_average + "</div>" +
          "</div>";
      }

      markup += "</div></div>";

      return markup;
    }

    function formatMovieSelection (movie) {
      return movie.title + (movie.release_date.length > 0 ? " (" + movie.release_date.substring(0,4) + ")" : "") || movie.text;
    }

    var $movieSelect = $('#movieId');
    $movieSelect.select2({
      language: 'ru',
      theme:'bootstrap',
      ajax: {
        url: "https://api.themoviedb.org/3/search/movie",
        dataType: 'json',
        delay: 350,
        data: function (params) {
          return {
            query: params.term, // search term
            api_key: '4db6cc1f5320b14cca568ee348e99d61',
            language: 'ru',
            page: params.page
          };
        },
        processResults: function (data, params) {
          // parse the results into the format expected by Select2
          // since we are using custom formatting functions we do not need to
          // alter the remote JSON data, except to indicate that infinite
          // scrolling can be used
          params.page = params.page || 1;

          return {
            results: data.results,
            pagination: {
              more: (params.page * 20) < data.total_results
            }
          };
        },
        cache: true
      },
      escapeMarkup: function (markup) { return markup; },
      minimumInputLength: 3,
      templateResult: formatMovie,
      templateSelection: formatMovieSelection
    });

$movieSelect.on("select2:select", function(event) {
  var movie = event.params.data;
  $('#movieTitle').val(movie.title);
  $('#originalTitle').val(movie.original_title);
  $('#posterPath').val(movie.poster_path);
  $('#overview').val(movie.overview);
  $('#releaseYear').val(movie.release_date.substring(0,4));
  $('#rating').val(movie.vote_average);
});
$(function(){

    const appendBook = function(data){
        var bookCode = '<a href="#" class="book-link" data-id="' +
            data.id + '">' + data.taskName + '</a>' + '<button id="delete">Удалить</button><br>';
        $('#book-list').append('<div>' + bookCode + '</div>');
    };

    $(".delete").click(function(){
        alert("WWW");
    });

//    Loading books on load page
//    $.get('/tasks/', function(response)
//    {
//        for(i in response) {
//            appendBook(response[i]);
//        }
//    });

    //Show adding book form
    $('#show-add-book-form').click(function(){
        $('#book-form').css('display', 'flex');
    });

    //Closing adding book form
    $('#book-form').click(function(event){
        if(event.target === this) {
            $(this).css('display', 'none');
        }
    });

    //Getting task
    $(document).on('click', '.book-link', function(){
        var link = $(this);
        var bookId = link.data('id');
        $.ajax({
            method: "GET",
            url: '/tasks/' + bookId,
            success: function(response)
            {
                var code = '<br><span class="executor">Исполнитель: ' + response.executorName + '</span>' + '<br>';
                var code2 = '<span class="create-time">Дата создания: ' + response.dateTime + '</span>' + '<br>';
                var code3 = '<span class="dead-line">Срок: ' + response.deadLine + ' дн.</span>';
                link.parent().append(code);
                link.parent().append(code2);
                link.parent().append(code3);

            },
            error: function(response)
            {
                if(response.status == 404) {
                    alert('Задача не найдена!');
                }
            }
        });
        return false;
    });

    //Adding task
    $('#save-book').click(function()
    {
        var data = $('#book-form form').serialize();
        $.ajax({
            method: "POST",
            url: '/tasks/',
            data: data,
            success: function(response)
            {
                $('#book-form').css('display', 'none');
                var book = {};
                book.id = response;

                var dataArray = $('#book-form form').serializeArray();
                for(i in dataArray) {
                    book[dataArray[i]['name']] = dataArray[i]['value'];
                }
                appendBook(book);
            }
        });
        return false;
    });

    //Delete task
//    $(document).on('click', '#delete', function(){
//            console.log( "ready!" );
//    });

//    $('#delete').click(function(){
//        console.log("ready!");
//    });
});
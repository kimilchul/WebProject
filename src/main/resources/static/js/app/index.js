var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-heart').on('click', function () {
            _this.heart();
        });

        $('#btn-comment').on('click', function () {
            _this.comment();
        });

        $('#btn-update').on('click', function () {
            _this.update();
        });

        $('#btn-delete').on('click', function () {
            _this.delete();
        });
    },
    save : function () {
        var formData = new FormData();

        var photo = $('#photo')[0].files;
        for (var i = 0; i < photo.length; i++) {
            formData.append('photo', photo[i]);
        }

        var dto = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        formData.append('dto', new Blob([JSON.stringify(dto)], { type: 'application/json' }));

        $.ajax({
            type: 'POST',
            url: '/api/v1/post',
            data: formData,
            processData: false,
            contentType: false,
        }).done(function() {
            alert('글이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update : function () {
        var formData = new FormData();

        var photo = $('#photo')[0].files;
        for (var i = 0; i < photo.length; i++) {
            formData.append('photo', photo[i]);
        }

        var dto = {
            postId: $('#postId').val(),
            title: $('#title').val(),
            content: $('#content').val()
        };

        var id = $('#postId').val();

        formData.append('dto', new Blob([JSON.stringify(dto)], { type: 'application/json' }));

        //

        $.ajax({
            type: 'PUT',
            url: '/api/v1/post/'+id,
            data: formData,
            processData: false,
            contentType: false,
        }).done(function() {
            alert('글이 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    comment : function () {
        var data = {
            postId: $('#id').val(),
            commentAuthor: $('#commentAuthor').val(),
            commentContent: $('#commentContent').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'POST',
            url: '/api/v1/post/'+id+'/comment',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('댓글이 등록되었습니다.');
            window.location.reload();
        }).fail(function (error) {
            window.location.reload();
        });
    },
    heart : function () {
        var data = {
            postsId: $('#id').val(),
            userEmail: $('#userEmail').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'POST',
            url: '/api/v1/post/'+id+'/heart',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('추천이 등록되었습니다.');
            window.location.reload();
        }).fail(function (error) {
            window.location.reload();
        });
    },
    delete : function () {
        var id = $('#postId').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/post/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('글이 삭제되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }

};



main.init();
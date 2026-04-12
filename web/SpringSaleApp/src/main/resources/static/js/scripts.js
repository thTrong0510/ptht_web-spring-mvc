function deleteProductById(url) {
    if(confirm("ban chac chan xoa khong ?") === true) {
        fetch(url, {
            method: 'delete'
        }).then(res => {
            if(res.status === 204)
                location.reload();
            else
                alert("he thong co loi vui long quay lai sau");
        });
    }
}
var holder = document.getElementById('holder'),
    state = document.getElementById('status');

if (typeof window.FileReader === 'undefined') {
    state.className = 'fail';
} else {
    state.className = 'success';
    state.innerHTML = '';
}

holder.ondragover = function () { this.className = 'hover'; return false; };
holder.ondragend = function () { this.className = ''; return false; };
holder.ondrop = function (e) {
    this.className = '';
    e.preventDefault();

    var file = e.dataTransfer.files[0],
        reader = new FileReader();
    console.log("file"+file)
    console.log("reader"+reader)

    reader.onload = function (event) {
        console.log(event.target);
        holder.style.background = 'url(' + event.target.result + ') no-repeat center';
    };

    reader.readAsDataURL(file);
    console.log("reader"+reader)


    console.log(file);
    let formData = new FormData();
    formData.append('file', file, file.name)
    console.log("formData"+ formData)
    console.log(formData.get('file'))


    async function uploadData() {

        const response = await axios.post('/uploadAjax', formData,
            {cache: false,
                processData: false,
                data:formData,
                contentType: false}
        )
        const data = response.data

        showUploadFile(data)

    }



    uploadData()


    return false;
};

const uploadUL = document.querySelector(".uploadResult ul")

function showUploadFile(arr){

    console.log('-------------arr-------------------')
    console.log("arr: " + arr)
    console.log(arr.uuid)


    let str = "";

    for(let i = 0; i < arr.length; i++){

        const obj = arr[i];
        console.log("obj : " + obj)
        var fileCallPath =  encodeURIComponent( obj.savePath+ "/s_"+obj.uuid +"_"+obj.fileName)


        str +=
            str +=
                `<li data-path=${obj.savePath} data-uuid=${obj.uuid} data-filename=${obj.fileName} data-imagelabel=${obj.imageLabel} data-selfdrawing="false"><div>
                  <span>${obj.fileName}</span>
                 <button type='button' data-file=${fileCallPath} data-type='image' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>
                 <img src='/display?fileName=${fileCallPath}'>
                 </div>
                 </li>`

    }

    //console.log(str)
    uploadUL.innerHTML += str
}


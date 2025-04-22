
        /*
        $(document).ready(function(){
            $("input[name=cartChkBox]").change( function(){
                getOrderTotalPrice();
            });
        });
        */

        document.addEventListener("DOMContentLoaded", function(){
            document.querySelectorAll("input[name=cartChkBox]").forEach((elem) => {
                elem.addEventListener('change', function() {
                    getOrderTotalPrice();                    

                    var isCheckAll = true;

                    document.querySelectorAll("input[name=cartChkBox]").forEach(function(elem){
                        if(elem.checked == false){
                            isCheckAll = false;                    
                        }
                    });            

                    if(isCheckAll){
                        document.querySelector("#checkall").checked = true;
                    }else{
                        document.querySelector("#checkall").checked = false;
                    }
                });
            });                  
        });

        function getOrderTotalPrice(){
            var orderTotalPrice = 0;

            /* 
            $("input[name=cartChkBox]:checked").each(function() {

                console.log(this);

                var cartItemId = $(this).val();
                var price = $("#price_" + cartItemId).attr("data-price");
                var count = $("#count_" + cartItemId).val();
                orderTotalPrice += price*count;
            });

            $("#orderTotalPrice").html(orderTotalPrice+'원'); 
            */

            document.querySelectorAll("input[name=cartChkBox]:checked").forEach(function(elem){
                var cartItemId = elem.value;
                var price = document.querySelector("#price_" + cartItemId).getAttribute("data-price");
                var count = document.querySelector("#count_" + cartItemId).value;
                orderTotalPrice += price*count;
            }); 

            document.querySelector("#orderTotalPrice").textContent = orderTotalPrice+'원';
        }

        function changeCount(obj){
            var count = obj.value;
            var cartItemId = obj.id.split('_')[1];

            /*
            var price = $("#price_" + cartItemId).data("price");
            var totalPrice = count*price;
            $("#totalPrice_" + cartItemId).html(totalPrice+"원");
            */

            var price = document.querySelector("#price_" + cartItemId).getAttribute("data-price");
            var totalPrice = count*price;
            document.querySelector("#totalPrice_" + cartItemId).textContent = totalPrice+"원";
            
            getOrderTotalPrice();
            updateCartItemCount(cartItemId, count);
        }

        function checkAll(){
        
            /*
            if($("#checkall").prop("checked")){
                $("input[name=cartChkBox]").prop("checked",true);
            }else{
                $("input[name=cartChkBox]").prop("checked",false);
            }
            */

            if(document.querySelector("#checkall").checked){
                document.querySelectorAll("input[name=cartChkBox]").forEach(function(elem){
                    elem.checked = true;
                });
            }else{
                document.querySelectorAll("input[name=cartChkBox]").forEach(function(elem){
                    elem.checked = false;
                });
            }
            
            getOrderTotalPrice();
        }

        function updateCartItemCount(cartItemId, count){
            var token = document.querySelector("meta[name='_csrf']").getAttribute("content");;
            var header = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

            var url = "/cartItem/" + cartItemId+"?count=" + count;

            //var url = "login";

            /*
            $.ajax({
                url      : url,
                type     : "PATCH",
                beforeSend : function(xhr){
                    // 데이터를 전송하기 전에 헤더에 csrf값을 설정
                    xhr.setRequestHeader(header, token);
                },
                dataType : "json",
                cache   : false,
                success  : function(result, status){
                    console.log("cartItem count update success");
                    
                },
                error : function(jqXHR, status, error){

                    if(jqXHR.status == '401'){
                        alert('로그인 후 이용해주세요');
                        location.href ='/members/login';
                    } else{
                        alert('에러 발생: ');
                        //alert(jqXHR.responseJSON.message);
                    }

                }
            });
            */

            fetch(url, {
                method: 'PATCH',
                headers: {
                    'X-CSRF-TOKEN': token,  
                },
            })
            .then((response) => {
                if(response.status == '401'){
                    alert('로그인 후 이용해주세요');    
                    location.href='/members/login';     
                }else{
                    console.log("cartItem count update success");   
                }                
            }).catch((error) => {
                alert('Error:', error);
            });     
        }

        function deleteCartItem(obj){
            var cartItemId = obj.dataset.id;
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");

            var url = "/cartItem/" + cartItemId;

            $.ajax({
                url      : url,
                type     : "DELETE",
                beforeSend : function(xhr){
                    /* 데이터를 전송하기 전에 헤더에 csrf값을 설정 */
                    xhr.setRequestHeader(header, token);
                },
                dataType : "json",
                cache   : false,
                success  : function(result, status){
                    location.href='/cart';
                },
                error : function(jqXHR, status, error){

                    if(jqXHR.status == '401'){
                        alert('로그인 후 이용해주세요');
                        location.href='/members/login';
                    } else{
                        alert(jqXHR.responseJSON.message);
                    }

                }
            });
        }

        function orders(){
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");

            var url = "/cart/orders";

            var dataList = new Array();
            var paramData = new Object();

            $("input[name=cartChkBox]:checked").each(function() {
                var cartItemId = $(this).val();
                var data = new Object();
                data["cartItemId"] = cartItemId;
                dataList.push(data);
            });

            paramData['cartOrderDtoList'] = dataList;

            var param = JSON.stringify(paramData);

            $.ajax({
                url      : url,
                type     : "POST",
                contentType : "application/json",
                data     : param,
                beforeSend : function(xhr){
                    /* 데이터를 전송하기 전에 헤더에 csrf값을 설정 */
                    xhr.setRequestHeader(header, token);
                },
                dataType : "json",
                cache   : false,
                success  : function(result, status){
                    alert("주문이 완료 되었습니다.");
                    location.href='/orders';
                },
                error : function(jqXHR, status, error){

                    if(jqXHR.status == '401'){
                        alert('로그인 후 이용해주세요');
                        location.href='/members/login';
                    } else{
                        alert(jqXHR.responseJSON.message);
                    }

                }
            });
        }
$(document).ready(function (){
   let searchStr;
   let hotelIdClicked;
   let hotelName;
   let numRooms;
   let numGuests;
   let checkInDate;
   let checkOutDate;
   let roomType;
   let discount;
   let price;
   let map = new Map();
   let priceMap = new Map();
   let discountMap = new Map();
   let guests = [];
   let guestIds = [];
   let bookingId;
   let reviewBookingId;
    $("#searchBtn").click(function() {
        const searchLocation = $("#searchLocation").val();
        searchStr = searchLocation;
        //$("#searchLocation").val("");
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: 'http://localhost:8282/findHotel/' + searchLocation,
            success: function (result) {
                console.log(result);
                $("#hotelTbl tr").not(".header").remove();
                $.each(result, function (key, value) {
                    let amenities = "<ul id ='list'>";
                    let currentHotelId = value.hotelId;
                    $.each(value.amenities, function(key1, value1) {
                        amenities += "<li>" + value1.name + "</li>";
                    })
                    amenities += "</ul>"

                    $("#hotelTbl").append("<tr><td>"+ value.hotelName + "</td><td>" + value.averagePrice + "</td><td>"
                        +value.starRating + "</td><td>" +  amenities + "</td><td><img class ='imgLink' height='200' " +
                        "width='300' src='"+ value.imageURL +"'></td><td><a href='#' class='reviewLink' id='"+ currentHotelId +
                        "'>Reviews</a></td></tr>")
                })

            },
            error: function (err) {

            }
        });
    })

    $("#filterBtn").click(function () {
        const priceRange = parseInt($("#priceRange").val());
        const tblRow = $("#hotelTbl tr").not(".header");
        const amenities = $(".hotel_amenity");
        const amenArray = [];
        $.each(amenities, function(key1, value1) {
            if ($(this).prop("checked") === true) {
                let item = $(this).val().trim().toUpperCase();
                amenArray.push(item);
            }
        })

        $(tblRow).show();
        $.each(tblRow, function(key, value) {
            const hotelPrice = parseInt($(value).children("td").eq("1").text());
            const hotelRating = parseInt($(value).children("td").eq("2").text());
            console.log("Hotel No. " + (key + 1));
            let flag = 0;
            if (hotelPrice > priceRange) {
                $(value).hide();
            } else {
                $.each($(".star_rating"), function (key, value) {
                    if ($(this).prop("checked") == true) {
                        const rating = parseInt($(this).val());
                        if (rating == hotelRating) {
                            flag = 1;
                        }
                    }
                });
                if (flag === 0) {
                    $(this).hide();
                }
                const size = amenArray.length;
                let match = 0;
                $.each($(value).find("li"), function(key2, value2) {
                   const item =  $(this).text().toUpperCase();
                   console.log("Item: " +  item);
                   console.log("amenity size: " + size);
                   $.each(amenArray, function(key3, value3) {
                       console.log("amen: " + value3);
                       if (item == value3) {
                           match++;
                       }
                   })
                })
                console.log("Match: " + match);
                if (size == 0 || match < size) {
                    $(value).hide();
                }
            }
        })

    })

    $("#hotelTbl").on("click", ".imgLink", function() {
        $("#myModal").modal("toggle");
        hotelIdClicked = $(this).parent().parent().find('.reviewLink').attr('id');
        console.log("id Clicked");
        console.log(hotelIdClicked);
        console.log("=======================")
        hotelName = $(this).parent().parent().children("td").eq("0").text();
        numRooms = $("#noRooms").val();

        numGuests = $("#noGuests").val();

        checkInDate = $("#checkInDate").val();

        checkOutDate = $("#checkOutDate").val();

        $("#modal_hotelName").val(hotelName);
        $("#modal_noRooms").val(numRooms);
        $("#modal_noGuests").val(numGuests);
        $("#modal_checkInDate").val(checkInDate);
        $("#modal_checkOutDate").val(checkOutDate);
        $("#select_roomTypes option").remove();
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: 'http://localhost:8282/findHotelById/' + hotelIdClicked,
            success: function (result) {
                console.log(result);
                $.each(result.hotelRooms, function (key, value) {
                    $("#select_roomTypes").append("<option>" + value.type.name +"</option>");
                    map.set(value.type.name, value.hotelRoomId);
                    priceMap.set(value.type.name, value.price);
                    discountMap.set(value.type.name, value.discount);
                })
            },
            error: function (err) {

            }
        });

    })

    $("#addguest").click(function() {
        $("#myModal").modal("toggle");
        $("#guestModal").modal("toggle");
        $("#guestbody .container, hr").remove();
        const numGuests = parseInt($("#modal_noGuests").val());
        numRooms = parseInt($("#modal_noRooms").val())
        checkInDate = $("#modal_checkInDate").val();
        checkOutDate = $("#modal_checkOutDate").val();
        roomType = $("#select_roomTypes").val();
        for (let i = 1; i <= numGuests; i++) {
            $("#guestbody").append("<div class ='container mt-5' id='" + i + "'><h4>Guest "+ i + "</h4>" +
                "First Name: <input class='form-control firstname' type='text'/>" +
                "Last Name: <input class='form-control lastname' type='text'/>" +
                "Age: <input class='form-control age' type='text'/>" +
                "Gender: <br> <select class ='form-select gender'>" +
                "<option>Male</option>" +
                "<option>Female</option></select></div><hr>");
        }
        roomType = $("#select_roomTypes").val();
    })

    $("#add").click(function() {
        let firstname;
        let lastname;
        let age;
        let gender;
        $.each($("#guestbody .container"), function (key, value) {
            $.each($(value).find('.firstname, .lastname, .age, .gender'), function(key1, value1) {
                console.log(value1);
                if ($(value1).hasClass('firstname')) {
                    firstname = $(this).val();
                } else if ($(value1).hasClass('lastname')) {
                    lastname = $(this).val();
                } else if ($(value1).hasClass('age')) {
                    age = parseInt($(this).val());
                } else if ($(value1).hasClass('gender')) {
                    gender = $(this).val();
                }
            })
            let guest = {"firstName": firstname, "lastName" : lastname, "age" : age, "gender" : gender};
            guests.push(guest);
        })


        $("#guestModal").modal('toggle');
        $("#bookingHotelRoomModal").modal('toggle');
        price = priceMap.get(roomType);
        console.log("prev Price - " + price);
        console.log("room type - " + roomType);
        discount = discountMap.get(roomType);
        discount = discount * price / 100;
        console.log("discount - " + discount);
        price = price - discount;
        console.log("price is - " + price)
        $("#booking_hotelId").val(hotelIdClicked);
        $("#booking_hotelName").val(hotelName);
        $("#booking_noGuests").val(numGuests);
        $("#booking_noRooms").val(numRooms);
        $("#booking_checkInDate").val(checkInDate);
        $("#booking_checkOutDate").val(checkOutDate);
        $("#booking_roomType").val(roomType);
        $("#booking_discount").html(discount);
        $("#booking_price").html(price);
    })

    $("#confirmBtn").click(function () {
        console.log("guests ids ----------------")
        console.log(JSON.stringify(guestIds));

        let phoneNum = $("#booking_customerMobile").val();
        let hotelRoomId = map.get(roomType);
        price = priceMap.get(roomType);

        const today = new Date();
        const year = today.getFullYear();
        const month = today.getMonth() + 1; // JavaScript months are 0-indexed.
        const day = today.getDate();

        // Format the date as YYYY-MM-DD
        const formattedDate = `${year}-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}`;

        let booking = {"hotelId" : hotelIdClicked, "hotelName" : hotelName,"hotelRoomId" : hotelRoomId, "noRooms" : numRooms,
            "guests" : guests, "checkInDate" : checkInDate, "checkOutDate" : checkOutDate,"bookedOnDate" : formattedDate ,"status" : "UPCOMING",
            "price" : price, "discount" : discount, "customerMobile" : phoneNum, "roomType" : roomType};
        console.log("Booking object: ")
        console.log(JSON.stringify(booking))
        guestIds = [];
        $.ajax({
            type : "POST",
            contentType : "application/json",
            url : "http://localhost:8282/saveBooking",
            data : JSON.stringify(booking),
            dataType : "json",
            success : function(result) {
                console.log(result);
                bookingId = result.bookingId;
                $.each(result.guests, function(key, value) {
                    guestIds.push(value.guestId);
                })
                console.log("guestId Arrays ===============================")
                console.log(guestIds);
                console.log("==============================================")
                alert("Booking Cofirmed!");
                guests = [];
            },
            error  : function (e) {

            }

        });
    })

    $("#editBtn").click(function() {
        $("#bookingHotelRoomModal").modal('toggle');
        $("#myModal").modal('toggle');
    })


    $("#btn-upcoming").click(function() {
        $("#profile-show-table tr").remove();
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: 'http://localhost:8282/getBookings',
            success: function (result) {
                console.log(result);
                const today = new Date();

                const day = today.getDate();          // Day of the month (1-31)
                const month = today.getMonth() + 1;   // Month (0-11, so add 1 to get 1-12)
                const year = today.getFullYear();     // Full year
                const formattedDate = `${year}-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}`;
                const currentDate = new Date(formattedDate);
                currentDate.setHours(1, 1, 1,1);
                console.log("currentdate");
                console.log(currentDate);
                let needCompletes = [];

                $.each(result, function (key, value) {
                    if (value.status === "UPCOMING") {
                        const bookingDate = new Date(value.checkInDate);
                        bookingDate.setHours(1, 1, 1, 1);
                        console.log("bookingdate: ");
                        console.log(bookingDate);
                       if (bookingDate < currentDate) {
                           const bookingId = value.bookingId;
                           needCompletes.push(bookingId);
                           console.log("booking id " + bookingId + " added to array for complete");
                       } else {
                           $("#profile-show-table").append("<tr><td>" + value.bookingId + "</td><td>" + value.bookedOnDate + "</td><td>" +
                               value.checkInDate + "</td><td>" + value.checkOutDate + "</td><td>" + value.customerMobile + "</td><td>" + value.price + "</td><td>" + value.status
                               + "</td><td><input type='button' class='cancel' value='Cancel'</td></tr>");
                       }
                    }
                })

                $.ajax({
                    type : "POST",
                    contentType : "application/json",
                    url : "http://localhost:8282/completeBookings",
                    data : JSON.stringify(needCompletes),
                    dataType : "json",
                    success : function(result) {
                        needCompletes = [];
                    },
                    error  : function (e) {

                    }

                });

            },
            error: function (err) {

            }
        });
    })

    $("#btn-completed").click(function() {
        $("#profile-show-table tr").remove();
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: 'http://localhost:8282/getBookings',
            success: function (result) {
                console.log(result);
                $.each(result, function (key, value) {
                    if (value.status === "COMPLETED") {
                        $("#profile-show-table").append("<tr><td>"+ value.bookingId +"</td><td>"+ value.bookedOnDate +"</td><td>" +
                            value.checkInDate  + "</td><td>" + value.checkOutDate +"</td><td>"+ value.customerMobile +"</td><td>"+ value.price+"</td><td>" + value.status
                            + "</td><td><input type='button' class='review' value='Review'</td></tr>");
                    }
                })
            },
            error: function (err) {

            }
        });
    })

    $("#profile-show-table").on('click', '.cancel', function() {
        const bookingId = $(this).parent().parent().children('td').eq('0').text();
        $(this).parent().parent().remove();
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: 'http://localhost:8282/cancelBooking/' + bookingId,
            success: function (result) {
                console.log(result);

            },
            error: function (err) {

            }
        });
    })


    $("#profile-show-table").on('click', '.review', function() {
        reviewBookingId = $(this).parent().parent().children('td').eq('0').text();
        $("#reviewModal").modal('toggle');
        $("#review-rating").text(1);
    })

    $("#review-submit").click(function() {

        const overallRating = parseFloat($("#review-rating").text());
        const reviewContent = $("#review-text").val();

        const review = {"text" : reviewContent, "overallRating" : overallRating};

        $.ajax({
            type : "POST",
            contentType : "application/json",
            url : "http://localhost:8282/updateBookingReview/" + reviewBookingId,
            data : JSON.stringify(review),
            dataType : "json",
            success : function(result) {
                alert("Review Submitted!");
                const hotelId = result.hotelId;
                const reviewId = result.review.reviewId;
                review.reviewId = reviewId;
                $.ajax({
                    type : "POST",
                    contentType : "application/json",
                    url : "http://localhost:8282/updateHotelReview/" + hotelId,
                    data : JSON.stringify(review),
                    dataType : "json",
                    success : function(result) {
                        console.log(result);
                    },
                    error  : function (e) {

                    }

                });
            },
            error  : function (e) {

            }

        });

    })


    $("#hotelTbl").on("click", ".reviewLink", function(evt) {
        evt.preventDefault();
        $("#reviewHotelName").text("");
        $("#reviewbody *").remove();
        const id = $(this).attr('id');
        const name = $(this).parent().parent().children('td').eq('0').text();
        $("#reviewHotelName").text(name);
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: 'http://localhost:8282/findHotelById/' + id,
            success: function (result) {
                console.log(result);
                $.each(result.reviews, function (key, value) {
                    const rating = value.overallRating;
                    let stars = "";
                    let fullStars = Math.floor(rating);
                    let halfStars = (rating - fullStars) >= 0.5 ? 1 : 0;
                    let emptyStars = 5 - fullStars - halfStars;
                    //add full stars
                    for (let i = 0; i < fullStars; i++) {
                        stars += '<span class="fas fa-star"></span>';
                    }

                    // Add half star
                    if (halfStars) {
                        stars += '<span class="fas fa-star-half-alt"></span>';
                    }

                    for (let i = 0; i < emptyStars; i++) {
                        stars += '<span class="far fa-star"></span>';
                    }

                    $("#reviewbody").append("<div><label><b>Rating:</b>" + " " + stars + "</label></br><label><b>Comment:</b></label><span>"+ " " + value.text +
                        "</span></div><hr>"
                    );
                });
            },
            error: function (err) {

            }
        });
        $("#reviewModalHome").modal('toggle');
    })


    $("#review-question1, #review-question2, #review-question3, #review-question4").change(function() {
        const rating1 = parseFloat($("#review-question1").val());
        const rating2 = parseFloat($("#review-question2").val());
        const rating3 = parseFloat($("#review-question3").val());
        const rating4 = parseFloat($("#review-question4").val());

        $("#review-rating").text((rating1 + rating2 + rating3 + rating4) / 4);
    })

    $("#signupBtn").click(function() {
        let username = $("#signup_name").val();
        let password = $("#signup_password").val();
        let email = $("#signup_email").val();
        let roleName = $("#signup_role").val();
        let roleId = (roleName === "USER") ? 1 : 2;
        let role = {"roleId" : roleId, "roleName" : roleName};
        let roles = [];
        roles.push(role);
        let user = {"userName" : username, "userPassword" : password, "email" : email, "roles" : roles};
        $.ajax({
            type : "POST",
            contentType : "application/json",
            url : "http://localhost:8282/register" ,
            data : JSON.stringify(user),
            dataType : "json",
            success : function(result) {
                console.log("signup successes!")
                console.log(result);
            },
            error  : function (e) {

            }
        });
    })
})
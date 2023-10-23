<!DOCTYPE html>
<html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<head>
    <meta charset="ISO-8859-1">
    <title>Home Page of Travel Gig</title>
    <script src="https://code.jquery.com/jquery-3.7.0.min.js"
            integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    <script src="../js/hotel.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"><script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.13.2/jquery-ui.min.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" type="text/css" href="../css/home.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
</head>
<body>
<div class="container" style="margin-left:100px">
    <h1>Welcome to Travel Gig</h1>
    <h2>Search your desired hotel</h2>
</div>

<div class="container border rounded" style="margin:auto;padding:50px;margin-top:50px;margin-bottom:50px">
    <h3>Narrow your search results</h3>
    <div class="form-row">
        <div class="col-3">
            Hotel/City/State/Address <input class="form-control" type="text" id="searchLocation" name="searchLocation"/>
        </div>
        <div class="col-2">
            No. Rooms: <input class="form-control" type="number" id="noRooms" name="noRooms"/>
        </div>
        <div class="col-2">
            No. Guests: <input class="form-control" type="number" id="noGuests" name="noGuests"/>
        </div>
        <div class="col">
            Check-In Date: <input type="date" id="checkInDate" name="checkInDate"/>
        </div>
        <div class="col">
            Check-Out Date: <input type="date" id="checkOutDate" name="checkOutDate"/>
        </div>
        <input class="btn-sm btn-primary" type="button" id="searchBtn" value="SEARCH"/>
    </div>
</div>

<div class="row">
    <div class="col-2 border rounded" style="margin-left:50px;padding:25px">

        <br>
        <!--  Star Rating:
        <select class="form-control" id="filter_starRating">
            <option value=0>Select</option>
            <option value=1>1</option>
            <option value=2>2</option>
            <option value=3>3</option>
            <option value=4>4</option>
            <option value=5>5</option>
        </select><br>-->

        Star Rating:<br>
        <div class="form-check-inline">
            <label class="form-check-label">
                <input type="checkbox" class="star_rating form-check-input" id="1_star_rating" value=1>1
            </label>
        </div>
        <div class="form-check-inline">
            <label class="form-check-label">
                <input type="checkbox" class="star_rating form-check-input" id="2_star_rating" value=2>2
            </label>
        </div>
        <div class="form-check-inline">
            <label class="form-check-label">
                <input type="checkbox" class="star_rating form-check-input" id="3_star_rating" value=3>3
            </label>
        </div>
        <div class="form-check-inline">
            <label class="form-check-label">
                <input type="checkbox" class="star_rating form-check-input" id="4_star_rating" value=4>4
            </label>
        </div>
        <div class="form-check-inline">
            <label class="form-check-label">
                <input type="checkbox" class="star_rating form-check-input" id="5_star_rating" value=5>5
            </label>
        </div><br><br>

        Range:
        <div class="slidecontainer">
            <input type="range" min="1" max="500" value="500" class="slider" id="priceRange">
            <p>Price: $<span id="priceValue"></span></p>
        </div>

        <div class="form-check">
            <input type="checkbox" class="hotel_amenity form-check-input" id="amenity_parking" value="PARKING"/>
            <label class="form-check-label" for="amenity_parking">Parking</label><br>

            <input type="checkbox" class="hotel_amenity form-check-input" id="amenity_swimming_pool" value="SWIMMING POOL"/>
            <label class="form-check-label" for="amenity_swimming_pool">Swimming Pool</label><br>

            <input type="checkbox" class="hotel_amenity form-check-input" id="amenity_restaurant" value="RESTAURANT"/>
            <label class="form-check-label" for="amenity_restaurant">Restaurant</label><br>

            <input type="checkbox" class="hotel_amenity form-check-input" id="amenity_balcony" value="BALCONY"/>
            <label class="form-check-label" for="amenity_balcony">Balcony</label><br>

            <input type="checkbox" class="hotel_amenity form-check-input" id="amenity_bath_tub" value="BATH TUB"/>
            <label class="form-check-label" for="amenity_bath_tub">Bath Tub</label><br>

            <input type="checkbox" class="hotel_amenity form-check-input" id="amenity_travel_desk" value="TRAVEL DESK"/>
            <label class="form-check-label" for="amenity_travel_desk">Travel Desk</label><br>

            <input type="checkbox" class="hotel_amenity form-check-input" id="amenity_led" value="LED"/>
            <label class="form-check-label" for="amenity_led">LED</label><br>

            <input type="checkbox" class="hotel_amenity form-check-input" id="amenity_mini_bar" value="MINI BAR"/>
            <label class="form-check-label" for="amenity_mini_bar">Mini Bar</label><br>


        </div>

        <input style="margin-top:25px" class="btn btn-primary" type="button" id="filterBtn" value="FILTER"/>
    </div>


    <div class="col-7 border rounded" style="margin-left:50px;">
        <div style='text-align:center;font-size:20px;font-family:"Trebuchet MS", Helvetica, sans-serif'>List of Hotels:</div>

        <div id="listHotel">
            <table id ='hotelTbl' border="1">

                <tr class = "header">
                    <th>
                        Hotel Name
                    </th>
                    <th>
                        Price
                    </th>
                    <th>
                        Rating
                    </th>
                    <th>
                        Amenities
                    </th>
                    <th>
                        Image
                    </th>
                    <th>
                        Reviews
                    </th>

                </tr>

            </table>
        </div>

    </div>
</div>

<div class="modal" id="myModal">
    <div class="modal-dialog">
        <div class="modal-content">

            <!-- Modal Header -->
            <div class="modal-header">
                <h4 class="modal-title">Search Hotel Rooms</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>

            <!-- Modal body -->
            <div class="modal-body">
                <div class="col">
                    <input class="form-control" type="hidden" id="modal_hotelId"/>
                    Hotel Name: <input readonly="true" class="form-control" type="text" id="modal_hotelName"/>
                    No. Guests: <input class="form-control" type="number" id="modal_noGuests"/>
                    Check-In Date: <input class="form-control" type="date" id="modal_checkInDate"/>
                    Check-Out Date: <input class="form-control" type="date" id="modal_checkOutDate"/>
                    Room Type:
                    <select class="form-control" id="select_roomTypes">
                    </select>
                    No. Rooms: <input class="form-control" type="number" id="modal_noRooms"/>
                    <input style="margin-top:25px" class="btn btn-searchHotelRooms form-control btn-primary" type="button" id="addguest" value="Add Guest"/>
                </div>

            </div>

            <!-- Modal footer -->
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
            </div>

        </div>
    </div>
</div>


<div class="modal" id="guestModal">
    <div class="modal-dialog">
        <div class="modal-content">

            <!-- Modal Header -->
            <div class="modal-header">
                <h4 class="modal-title">Guest Information</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>

            <!-- Modal body -->
            <div class="modal-body">
                <div class="col" id="guestbody">

                </div>
                <input style="margin-top:25px" class="btn btn-searchHotelRooms form-control btn-primary" type="button" id="add" value="Add"/>
            </div>

            <!-- Modal footer -->
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
            </div>

        </div>
    </div>
</div>

<div class="modal" id="reviewModalHome">
    <div class="modal-dialog">
        <div class="modal-content">

            <!-- Modal Header -->
            <div class="modal-header">
                <h4 class="modal-title">Reviews for <span id="reviewHotelName"></span></h4>
                <button type="button" class="close reviewClose" data-dismiss="modal">&times;</button>
            </div>

            <!-- Modal body -->
            <div class="modal-body">
                <div class="col" id="reviewbody">

                </div>
            </div>

            <!-- Modal footer -->
            <div class="modal-footer">
                <button type="button" class="btn btn-danger reviewClose" data-dismiss="modal">Close</button>
            </div>

        </div>
    </div>
</div>

<div class="modal" id="hotelRoomsModal">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">

            <!-- Modal Header -->
            <div class="modal-header">
                <h4 class="modal-title">Are these details correct?</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>

            <!-- Modal body -->
            <div class="modal-body" id="hotelRooms_modalBody">

            </div>

            <!-- Modal footer -->
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
            </div>

        </div>
    </div>
</div>

<div class="modal" id="bookingHotelRoomModal">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">

            <!-- Modal Header -->
            <div class="modal-header">
                <h4 class="modal-title"></h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>

            <!-- Modal body -->
            <div class="modal-body" id="bookingRoom_modalBody">
                <div class="col">
                    <div><input class="form-control" type="hidden" id="booking_hotelId"/></div>
                    <div><input class="form-control" type="hidden" id="booking_hotelRoomId"/></div>
                    <div>Hotel Name: <input readonly="true" class="form-control" type="text" id="booking_hotelName"/></div>
                    <div>Customer Mobile: <input class="form-control" type="text" id="booking_customerMobile"/></div>
                    <div id="noGuestsDiv">No. Guests: <input readonly="true" class="form-control" type="number" id="booking_noGuests"/></div>
                    <div>No. Rooms: <input readonly="true" class="form-control" type="number" id="booking_noRooms"/></div>
                    <div>Check-In Date: <input readonly="true" class="form-control" type="text" id="booking_checkInDate"/></div>
                    <div>Check-Out Date: <input readonly="true" class="form-control" type="text" id="booking_checkOutDate"/></div>
                    <div>Room Type: <input readonly="true" class="form-control" type="text" id="booking_roomType"/></div>
                    <div>Discount: $<span id="booking_discount"></span></div>
                    <div>Total Price: $<span id="booking_price"></span></div>
                    <div style='margin-top:20px'>
                        <button class='btn-confirm-booking btn btn-primary' id = 'confirmBtn'>Confirm Booking</button>
                        <button class='btn btn-primary' id = 'editBtn'>Edit</button>
                    </div>
                </div>
            </div>

            <!-- Modal footer -->
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
            </div>

        </div>
    </div>
</div>

<script>
    var slider = document.getElementById("priceRange");
    var output = document.getElementById("priceValue");
    output.innerHTML = slider.value;
    slider.oninput = function() {
        output.innerHTML = this.value;
    }
</script>
</body>
</html>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Payment Details</title>
</head>
<body>

	<table border="1">
		<caption>Transaction Details</caption>
		<thead>
			<th>
			<td>Sr. No</td>
			<td>Title</td>
			<td>Value</td>
			</th>
		</thead>
		<tbody>
			<tr>
				<td>1</td>
				<td>Transaction Amount</td>
				<td>${amount}</td>
			</tr>
			<tr>
				<td>2</td>
				<td>Transaction Id</td>
				<td>${txn_id}</td>
			</tr>
			<tr>
				<td>3</td>
				<td>Address</td>
				<td>${address}</td>
			</tr>
			<tr>
				<td>4</td>
				<td>Remaining Confirmation</td>
				<td>${confirms_needed}</td>
			</tr>
			<tr>
				<td>5</td>
				<td>Transaction Status URL</td>
				<td>${status_url}</td>
			</tr>
			<tr>
				<td>6</td>
				<td>QR Code URL</td>
				<td>${qrcode_url}</td>
			</tr>
		</tbody>
	</table>
	<img alt="QR Code URL : ${qrcode_url}" src="${qrcode_url}"
		width="250px" hight="250px">
	<br>
	<br>
	<br>
	<center>
		<a href="/index.html">Back To Home Page</a>
	</center>
</body>
</html>
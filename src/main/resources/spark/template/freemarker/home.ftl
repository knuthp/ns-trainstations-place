<!DOCTYPE html>
<html>
<head>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

<script>
	$(document).ready(function() {
		$('input[type=button]').click(function() {
			var placeId = $(this).data('id');

			$.ajax({
				url : '/api/place/' + placeId,
				type : 'DELETE',
				success : function(result) {
					console.log('Success delete');
					location.reload();
				}
			});
		});
	});
</script>
<title>Places landing page</title>
</head>
<body>
	<h1>Places</h1>
	<h2>REST</h2>
	<table>
		<tr>
			<th>Method</th>
			<th>Path</th>
			<th>Description</th>
		</tr>
		<tr>
			<td>GET</td>
			<td><a href="api/place">api/place</a></td>
			<td>List all places</td>
		</tr>
		<tr>
			<td>POST</td>
			<td>api/place</td>
			<td>Add a new place</td>
		</tr>
		<tr>
			<td>GET</td>
			<td><a href="api/place/3010030">api/place/&lt;id&gt;</a></td>
			<td>Get data about one place</td>
		</tr>
		<tr>
			<td>DELETE</td>
			<td><a href="api/place/3010030">api/place/&lt;id&gt;</a></td>
			<td>Delete one place</td>
		</tr>
	</table>

	<h2>Status</h2>
	<table>
		<tr>
			<th>#</th>
			<th>Id</th>
			<th>Name</th>
			<th>Short name</th>
		</tr>
		<#list places as place>
		<tr>
			<td>${place_index + 1}.</td>
			<td>${place.id}</td>
			<td>${place.name}</td>
			<td><input type="button" value="Delete" data-id="${place.id}"</td>
		</tr>
		</#list>
	</table>
	<h2>Add new</h2>
	<form id="place-create-form" method="POST" action="api/place">
		Id: <input type="text" name="id" /> <input type='submit'
			value='Add' form='place-create-form' />
	</form>

</body>
</html>
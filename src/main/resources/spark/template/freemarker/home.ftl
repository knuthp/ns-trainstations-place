<html>
<head>
  <title>Places landing page</title>
</head>
<body>
<h1>Places</h1>
<h2>REST</h2>
  <table>
    <tr><th>Method</th><th>Path</th><th>Description</th></tr>
    <tr><td>GET</td><td><a href="api/place">api/place</a></td><td>List all places</td></tr>
    <tr><td>POST</td><td>api/place</td><td>Add a new place</td></tr>
    <tr><td>GET</td><td><a href="api/place/3010030">api/place/&lt;id&gt;</a></td><td>Get data about one place</td></tr>
  </table>

<h2>Status</h2>
<ul>
    <#list places as place>
      <li>${place_index + 1}. ${place.name} | ${place.shortName}</li>
    </#list>
  </ul>
</body>
</html>
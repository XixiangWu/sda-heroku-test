<!-- Header -->
<!doctype html>
<html class="no-js" lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>View All Issues</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="css/shards.min.css?v=3.0.0">
    <link rel="stylesheet" href="css/shards-demo.min.css?v=3.0.0">
</head>

<!-- Navigation Bar -->
${navbar}

<!-- Body -->
<!-- Badges -->
<div id="badges" class="container">
    <div class="section-title col-lg-8 col-md-10 ml-auto mr-auto">
        <h1 class="mb-4"> <strong>Issue: </strong>${issue_title}</h1>
        <h5 class="mb-4"><strong>Submitter</strong></h5>
        <p>${user}<p>
        <h5 class="mb-4"><strong>Status</strong></h5>
        <p>${appointment_status}<p>
        <h5 class="mb-4"><strong>Issue Description</strong></h5>
        <p>${issue_description}<p>
        <h5 class="mb-4"><strong>Appointment submit time</strong></h5>
        <p>${appointment_time_submitted}</p>
        <h5 class="mb-4"><strong>Estimate Appointment Time</strong></h5>
        <p>${appointment_time_estimated}</p>
        <p>${buttons}</p>
    </div>

    <div class="example col-md-10 ml-auto mr-auto mb-5 pb-4">
        <div class="row mb-3">
            <div class="d-table ml-auto mr-auto">
            </div>
        </div>
    </div>
</div>



${script}

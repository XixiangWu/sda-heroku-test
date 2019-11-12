<!-- Header -->
<!doctype html>
<html class="no-js" lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Shards Demo - A free and modern UI toolkit for web makers</title>
    <meta name="description" content="A free and modern UI toolkit for web makers based on the popular Bootstrap 4 framework.">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="css/shards.min.css?v=3.0.0">
    <link rel="stylesheet" href="css/shards-demo.min.css?v=3.0.0">
</head>

<!-- Navigation Bar -->
${navbar}

<div id="table" class="container">
    <div class="section-title col-lg-12 col-md-10 ml-auto mr-auto">
        <h3 class="mb-4">Submit New Appointment</h3>
        <p>Please describe why you need an onsite visit.</p>
    </div>

    <div class="example col-lg-auto col-md-1 ml-auto mr-auto">
        <div class="row">
            <form action='' method='post'>
                <div class='form-group row'>
                    <div class='col-md-12'>
                        <p>Appointment ID:</p><textarea id='appointmentid' class='form-control' rows='1' name='appointmentid' readonly> ${appointmentid}</textarea>
                    </div>
                </div>
                <div class="col-md-12 pl-0 custom-dropdown-example">
                    <label for='description' class='col-md-1 col-form-label text-md-right'>Select</label>
                    <fieldset>
                        <select disabled="disabled" id='issueid' name='issueid' class="custom-select w-100" required>
                            ${options}
                        </select>
                    </fieldset>
                </div>
                <div class='form-group row'>
                    <label for='description' class='col-md-1 col-form-label text-md-right'>Description</label>
                    <div class='col-md-12'>
                        <textarea id='description' class='form-control' rows='10' name='description' required readonly>${description}</textarea>
                    </div>
                </div>
                <div class="col-md-12 pl-0 custom-dropdown-example">
                    <label for='description' class='col-md-1 col-form-label text-md-right'>Choose Date</label>
                    <fieldset>
                        <select id='date' name='date' class="custom-select w-100" required>
                            ${dateOptions}
                        </select>
                    </fieldset>
                </div>
                <div class="col-md-12 pl-0 custom-dropdown-example">
                    <label for='description' class='col-md-1 col-form-label text-md-right'>Choose Time</label>
                    <fieldset>
                        <select id='time' name='time' class="custom-select w-100" required>
                            ${timeOptions}
                        </select>
                    </fieldset>
                </div>
                <div class='col-md-6 offset-md-4'>
                    <button type='submit' class='btn btn-primary'>Submit</button>
                </div>
            </form>
        </div>
    </div>
</div>

${script}
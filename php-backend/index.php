<?php

require 'vendor/autoload.php';

use GlobalPayments\Api\ServicesConfig;
use GlobalPayments\Api\HostedPaymentConfig;
use GlobalPayments\Api\Services\HostedService;
use GlobalPayments\Api\Entities\Enums\HppVersion;
use GlobalPayments\Api\Entities\Exceptions\ApiException;

$config = new ServicesConfig();
$config->merchantId = "heartlandgpsandbox";
$config->accountId = "hpp";
$config->sharedSecret = "secret";
$config->serviceUrl = "https://pay.sandbox.realexpayments.com/pay";
$config->hostedPaymentConfig = new HostedPaymentConfig();
$config->hostedPaymentConfig->version = 2; // HppVersion::VERSION_2;
$service = new HostedService($config);

header('Content-Type: application/json');

if (stripslashes($_SERVER['REQUEST_URI']) == '/index.php/hppRequestProducer') {
    try {
        $hppJson = $service->charge(19.99)
            ->withCurrency("EUR")
            ->serialize();

        $json = (array)json_decode($hppJson);
        unset($json['HPP_VERSION']);
        
        echo json_encode($json);
    } catch (ApiException $e) {
        echo json_encode([
            'error' => true,
            'message' => $e->getMessage(),
        ]);
    }
    exit();
}

echo json_encode($_SERVER);

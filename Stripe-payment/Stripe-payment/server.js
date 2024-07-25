const express = require("express");
const app = express();
// This is your test secret API key.
const stripe = require("stripe")('sk_test_51OC2IWIfaabPwSbuUpk7zk6zic9fG6RWdltAh6Bwj58YOtnmeCEvyofZ5l28OMOcwEm0pSKX2qMxv2NOr7hCnbUP00yKBAKw7N');

app.use(express.static("public"));
app.use(express.json());

const calculateOrderAmount = (items) => {
    // Replace this constant with a calculation of the order's amount
    // Calculate the order total on the server to prevent
    // people from directly manipulating the amount on the client
    return items[0].amount;
};

app.post("/create-payment-intent", async (req, res) => {
    const { items } = req.body;

    // Create a PaymentIntent with the order amount and currency
    const paymentIntent = await stripe.paymentIntents.create({
        amount: calculateOrderAmount(items),
        currency: "usd",
        payment_method_types: ['card'],
    });

    res.send({
        clientSecret: paymentIntent.client_secret,
    });
});

app.listen(4242, () => console.log("Node server listening on port 4242!"));
# NPL

This project is intended to process text via a natural language processor. 
Currently, this application only uses AWS Comprehend to do the processing.

## Example Text
Text input (since this is a CLI app, ensure that the text does not have new lines): 

```
I cannot say enough good things about Spot Hero. Not only is the app easy to use, it takes the worry of the cost and accessibility of parking away. It makes the process so much smoother and more pleasant. I live outside NYC and have used it many times there but I have used it in other cities as well.

Recently, the garage I had arranged for was inaccessible due to street closures. I was late for work and didn’t have time to call Spot Hero to make other arrangements so I just pulled into another garage and paid almost 3 times as much. I knew that if I called Spot Hero I would get my original payment refunded.

When I finally had a chance to call, they refunded my original payment and when I provided a receipt, gave me a credit for the additional cost of the parking garage that I wound up using. Hard to find such good customer service these days and it has made me a loyal customer who will recommend them to anyone who will listen.
```

Key Phrase Output:
```
{Score: 0.99996954,Text: enough good things,BeginOffset: 14,EndOffset: 32}, 
{Score: 0.9999964,Text: Spot Hero,BeginOffset: 39,EndOffset: 48}, 
{Score: 0.9999915,Text: the app,BeginOffset: 62,EndOffset: 69}, 
{Score: 0.9999997,Text: the worry,BeginOffset: 92,EndOffset: 101}, 
{Score: 0.9999944,Text: the cost and accessibility,BeginOffset: 105,EndOffset: 131}, 
{Score: 0.91381484,Text: parking,BeginOffset: 135,EndOffset: 142}, 
{Score: 0.99999845,Text: the process,BeginOffset: 158,EndOffset: 169}, 
{Score: 0.9999999,Text: NYC,BeginOffset: 221,EndOffset: 224}, 
{Score: 0.9991967,Text: many times,BeginOffset: 242,EndOffset: 252}, 
{Score: 0.99999946,Text: other cities,BeginOffset: 281,EndOffset: 293}, 
{Score: 0.9999901,Text: the garage,BeginOffset: 313,EndOffset: 323}, 
{Score: 0.99996555,Text: street closures,BeginOffset: 367,EndOffset: 382}, 
{Score: 0.99992394,Text: work,BeginOffset: 399,EndOffset: 403}, 
{Score: 0.9999497,Text: time,BeginOffset: 420,EndOffset: 424}, 
{Score: 0.99997616,Text: Spot Hero,BeginOffset: 433,EndOffset: 442}, 
{Score: 1.0,Text: other arrangements,BeginOffset: 451,EndOffset: 469}, 
{Score: 0.9999994,Text: another garage,BeginOffset: 492,EndOffset: 506}, 
{Score: 0.9270938,Text: almost 3 times as much,BeginOffset: 516,EndOffset: 538}, 
{Score: 0.9999315,Text: Spot Hero,BeginOffset: 564,EndOffset: 573}, 
{Score: 0.99999976,Text: my original payment,BeginOffset: 586,EndOffset: 605}, 
{Score: 1.0,Text: a chance,BeginOffset: 635,EndOffset: 643}, 
{Score: 0.9999998,Text: my original payment,BeginOffset: 667,EndOffset: 686}, 
{Score: 0.9999999,Text: a receipt,BeginOffset: 707,EndOffset: 716}, 
{Score: 0.99999803,Text: a credit,BeginOffset: 726,EndOffset: 734}, 
{Score: 0.99999785,Text: the additional cost,BeginOffset: 739,EndOffset: 758}, 
{Score: 0.99999434,Text: the parking garage,BeginOffset: 762,EndOffset: 780}, 
{Score: 0.94935787,Text: such good customer service,BeginOffset: 817,EndOffset: 843}, 
{Score: 0.9999952,Text: these days,BeginOffset: 844,EndOffset: 854}, 
{Score: 0.9994914,Text: a loyal customer,BeginOffset: 874,EndOffset: 890}
```

Sentiment Output:
```
{
    Sentiment: POSITIVE,
    SentimentScore: {
        Positive: 0.9969283,
        Negative: 0.0024464617,
        Neutral: 6.236479E-4,
        Mixed: 1.6183192E-6
    }
}

```

## Requirements
1. Ensure your aws credentials are setup in your user root at [~/.aws/credentials](https://docs.aws.amazon.com/cli/latest/userguide/cli-configure-files.html)
2. Java 13

## Building and Running
1. `make build-jar`
2. Key Phrase Detection: `make run-key-phrase`
3. Sentiment Detection: `make run-sentiment`

import pytest
import asyncio
from unittest.mock import patch
from bank_system import Account, Bank, InsufficientFundsError


@pytest.fixture
def bank():
    return Bank()


@pytest.fixture
def account1(bank):
    return bank.create_account("12345", "Jan Kowalski", 1000.0)


@pytest.fixture
def account2(bank):
    return bank.create_account("67890", "Anna Nowak", 500.0)


@pytest.mark.asyncio
def test_deposit(account1):
    account1.deposit(500.0)
    assert account1.balance == 1500.0

@pytest.mark.asyncio
def test_withdraw(account1):
    account1.withdraw(300.0)
    assert account1.balance == 700.0

@pytest.mark.asyncio
def test_withdraw_insufficient_funds(account1):
    with pytest.raises(InsufficientFundsError):
        account1.withdraw(1500.0)


@pytest.mark.asyncio
async def test_transfer(account1, account2):
    await account1.transfer(account2, 300.0)
    assert account1.balance == 700.0
    assert account2.balance == 800.0


@pytest.mark.asyncio
async def test_transfer_insufficient_funds(account1, account2):
    with pytest.raises(InsufficientFundsError):
        await account1.transfer(account2, 1500.0)


def test_create_account(bank):
    account = bank.create_account("12345", "Joe Doe", 300.0)
    assert account.account_number == "12345"
    assert account.balance == 300.0


def test_get_account(bank, account1):
    fetched_account = bank.get_account("12345")
    assert fetched_account == account1


def test_get_account_nonexistent(bank):
    with pytest.raises(ValueError, match="Account with this id already exists"):
        bank.get_account("99999")


@pytest.mark.asyncio
async def test_process_transaction(bank, account1, account2):
    async def transaction_func():
        await account1.transfer(account2, 200.0)

    await bank.process_transaction(transaction_func)
    assert account1.balance == 800.0
    assert account2.balance == 700.0


def test_insufficient_funds(account1):
    with pytest.raises(InsufficientFundsError):
        account1.withdraw(1500.0)


def test_invalid_account_number(bank):
    bank.create_account("12345", "Jan Kowalski", 1000.0)

    with pytest.raises(ValueError, match="Account with this id already exists"):
        bank.create_account("12345", "Karol karol", 500.0)



def mock_external_authorization(*args, **kwargs):
    return True


from unittest.mock import patch


def mock_external_authorization(*args, **kwargs):
    return True


@patch("bank_system.Bank.authorize_transaction")
def test_mocked_transaction(mock_authorize, bank, account_a, account_b):
    bank.create_account(account_a.account_number, account_a.owner, account_a.balance)
    bank.create_account(account_b.account_number, account_b.owner, account_b.balance)

    async def transaction():
        await account_a.transfer(account_b, 20.0)

    if(mock_authorize.return_value):
        asyncio.run(bank.process_transaction(transaction))
        assert account_a.balance == 80.0
        assert account_b.balance == 70.0
    else:
        with pytest.raises(ValueError):
            asyncio.run(bank.process_transaction(transaction))

def test_invalid_account_number(bank):
    with pytest.raises(ValueError):
        bank.get_account("invalid_account")


